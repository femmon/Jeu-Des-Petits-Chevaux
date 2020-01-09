package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.*;
import view.*;


import java.io.IOException;
import java.util.ArrayList;

public class GameController {

    Stage stage;
    static GameController controller;
    GameView gameView;
    GameModel gameModel = new GameModel();
    ArrayList<ImageView> horseOnTrack = new ArrayList<>();

    private ArrayList<Player> playerList = new ArrayList<>();
//    private ArrayList<ArrayList <Player>> playerLists = new ArrayList<>();;

    private GameController() throws IOException {
        // Create game view
        HBox board = FXMLLoader.load(getClass().getResource("../view/pachisi.fxml"));
        gameView = new GameView(board);

        // Create setting controller and pass in board to display after setting
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../view/LanguageSettingView.fxml")));
        Parent root = loader.load();
        settingController controller = loader.getController();
        controller.initData(this, board);

        stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Settings");

        //primaryScene.getStylesheets().add(getClass().getResource("/view/debug.css").toExternalForm());
        controllerDemo(gameView);
    }

    public static GameController getInstance() throws IOException {
        if (controller == null)
            controller = new GameController();
        return controller;
    }

    public void update() {
            stage.show();
    }

    /**
     * The index labeling :
     * 0 : Red nest/horse/homePath
     * 1 : Green nest/horse/homePath
     * 2 : Blue nest/horse/homePath
     * 3 : Yellow nest/horse/homePath
     * @param view
     */

    //-------------------------VIEW DEMO------------------------------
    private void controllerDemo(GameView view) {
        view.pathEvents();
        view.homePathEvent(2);
        view.homePathEvent(1);
        summonHorseFromNest(3);


        String[] destinations = {"0xffa500ff_8", "0x0000ffff_2"};
        for (String destination: destinations) {
            getHorseIDOnClick(destination);
        }
    }

    public ArrayList<ImageView> getHorsesOnTrack(ImageView horse) {
        horseOnTrack.add(horse);
        return horseOnTrack;
    }

    //-------------------------SUMMONING HORSE------------------------------

    // calling horse to summon
    public void summonHorseFromNest(int index) {
        gameView.getNest(index).getHorseStable().setOnMouseClicked(e -> {
            Node chosenHorse = e.getPickResult().getIntersectedNode();
            if (chosenHorse instanceof ImageView) {
                standOnStartingPoint((ImageView) chosenHorse, index * 2);
            }
        });
    }

    // cho ngựa xuất chuồng
    public void standOnStartingPoint(ImageView horseImage, int index) {
        System.out.println(horseImage + " is standing at " + gameView.getAllPaths()[index].getPathContents().get(0).getId());
        StackPane startingStep = (StackPane) gameView.getAllPaths()[index].getPathContents().get(0);
        startingStep.getChildren().add(horseImage); // one horse is added at starting point
        getHorsesOnTrack(horseImage);
    }

    //-------------------------MOVING HORSE ON DEMAND------------------------------
    /**
     * Every ID has the following convention: colorCode + "_" + positionCode
     *
     * Path ID format
     * 0xff0000ff - red
     * 0x008000ff - green
     * 0x0000ffff - blue
     * 0xffa500ff - orange
     *
     * For ex: The id of each home path is: 0xffa500ff_11 -> 0xffa500ff_17
     * */

    /**
     * Horse ID format ex: red_0 -> red_3, yellow_0 -> yellow_3
     * */

    //      int[] pathRoute = {0, 2, 3, 1};


    private String[] processingID(String wholeCode) {
        String colorCode = wholeCode.substring(0, wholeCode.length() - 2);
        String positionCode = wholeCode.substring(wholeCode.length() - 1);
        return new String[]{colorCode, positionCode};
    }

    // click a horse to get ID

      private void getHorseIDOnClick(String destination) {
            for (PathView path: gameView.getAllPaths()) {
                for (Node pathContents: path.getPathContents()) {
                    pathContents.setOnMouseClicked(e -> {
                        Node chosenItem = e.getPickResult().getIntersectedNode();
                        if (chosenItem instanceof ImageView) {
                            moveAsDemand(destination, chosenItem.getId());
                        }
                        else {
                            System.out.println("No horse shown on the path");
                        }
                    });
                }
            }
      }
      
      private int convertPathIDtoPPathIndex(String[] pathID) {
        int pathIndex = 0;
          switch (pathID[0]) {
              case "0xff0000ff":
                  break;
              case "0x008000ff":
                  pathIndex = 2;
                  break;
              case "0x0000ffff":
                  pathIndex = 4;
                  break;
              case "0xffa500ff":
                  pathIndex = 6;
                  break;
          }
          if (Integer.parseInt(pathID[1]) > 5) {
              pathIndex++;
          }
          return pathIndex;
      }

      

      private void moveAsDemand(String pathID, String horseID) {
            if (!horseOnTrack.isEmpty()) {
                // processing path ID
                String[] pathIDPackage = processingID(pathID);  // 0 is colorCode and 1 is positionCode
                // processing horse ID
                String[] horseIDPackage = processingID(horseID); // same as above
                 // 1, 2, 3, 4
                int pathIndex = convertPathIDtoPPathIndex(pathIDPackage);
                int nestIndex = 0;

                int horseOrder = Integer.parseInt(horseIDPackage[1]);

                switch (horseIDPackage[0]) {
                    case "red":
                        break;
                    case "green":
                        nestIndex = 1;
                        break;
                    case "blue":
                        nestIndex = 2;
                        break;
                    case "yellow":
                        nestIndex = 3;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + horseIDPackage[0]);
                }
                String oldPathID = "";
                // if I get the parent of the horse I will reveal its position 
                for (ImageView horse: horseOnTrack) {
                    if (horse.getId().equals(horseID)) {
                        oldPathID = horse.getParent().getId(); 
                    }
                }
                ImageView selectedHorse = gameView.getNest(nestIndex).getHorseList().get(horseOrder);
                String[] oldPosPackage = processingID(oldPathID);
                int oldPathIndex = convertPathIDtoPPathIndex(oldPosPackage);

                gameView.getAllPaths()[oldPathIndex].removeHorse(oldPathID);
                gameView.getAllPaths()[pathIndex].setHorse(pathID, selectedHorse);
            }
        }

    //Test Dice
    public void displayDice(byte diceValue) {
        RollDices rollDices = new RollDices(4);
        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(rollDices);

        Scene secondScene = new Scene(secondaryLayout, 300, 300);
        Stage newWindow = new Stage();
        newWindow.setTitle("Dice");
        newWindow.setScene(secondScene);
        newWindow.show();
    }

//-------------------------GAME PLAY------------------------------

    private void displayDiceValue(int dicevalue) {
    /*
    * Create a new window which popup to show the dice animation
    *
     */
        RollDices rollDices = new RollDices(dicevalue);
    }
    /*
     * get dice value
     * Display dice (Roll animation)
     * @return Dice obj
     */
    private Dice throwDice() {
        Dice dice = new Dice();
        dice.throwDice();
        return dice;
    }

    public boolean isSummon(int dice1, int dice2) {
        return dice1 == 6 || dice2 == 6;
    }

    public boolean isBonusTurn(int dice1, int dice2) {
        return dice1 == dice2;
    }

    //-----------------------Set player----------------------------

    private void setPlayer(String name, PlayerType playerType, Color color) {
        Player player = new Player(name, playerType, color);
        this.playerList.add(player);
    }

    public void setPlayerList(ArrayList<String> name, ArrayList<Boolean> human, ArrayList<Boolean> com) {
        Color[] list = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN};

        for (int i = 0; i < 4; i++) {
            PlayerType playerType;
            if (!human.get(i) && !com.get(i)) {
                playerType = PlayerType.NONE;
            } else if (human.get(i)) {
                playerType = PlayerType.HUMAN;
            } else {
                playerType = PlayerType.MACHINE;
            }

            setPlayer(name.get(i), playerType, list[i]);
        }

        for (Player p: playerList) {
            System.out.println(p.getName() + " " + p.getPlayerType());
        }
    }

    //-----------------------Set turn---------------------
    public void rollDiceForTurn() {
        for (int i = 0; i < playerList.size(); i++) {
            int diceValue = throwDice().getDiceValue();
            if (isDuplicateDiceValue(diceValue)) {
                i--;
                continue;
            }
            playerList.get(i).setDiceValue(diceValue);

        }
    }

    private boolean isDuplicateDiceValue(int diceValue) {
        for (int i = 0; i < playerList.size(); i++) {
            if (diceValue == playerList.get(i).getDiceValue()) {
                return true;
            }
        }
        return false;
    }

    private int findMaximumDiceValue() {
        int max = 0;
        for (Player player : playerList) {
            if (player.getDiceValue() > max) {
                max = player.getDiceValue();
            }
        }
        return max;
    }

    private int findPlayerWithHighestDice(int max) {
        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i).getDiceValue() == max) {
                return i;
            }
        }
        return -1;
    }
    //--------------------Game play methods---------------------
    private void displayDiceValue(int dicevalue1, int diceValue2) {
        /*
         * Create a new window which popup to show the dice animation
         * Have the button to choose dice
         */
    }

    /*
     * receive the dice value user choose
     * @return finalDiceValue
     */
    private int pickDicevalue(Dice dice1, Dice dice2) {
        if (!dice1.isPicked() && !dice2.isPicked()) {
            dice1.setPickedDice(true);
            dice2.setPickedDice(true);
            return dice1.getDiceValue() + dice2.getDiceValue();
        } else if (!dice1.isPicked()) {
            dice1.setPickedDice(true);
            return dice1.getDiceValue();
        } else if(!dice2.isPicked()) {
            dice2.setPickedDice(true);
            return dice2.getDiceValue();
        } else {
            return -1;
        }
    }

    public ArrayList<Horse> findAllHorse(Player player, Board board) {
        ArrayList<Horse> horseInBoardList = new ArrayList<Horse>();
        for (int i = 0; i < 4; i++) {
            PathNode NodeWithHorse = board.findHorseInPath(player.getPlayerSide(), i);
            if (NodeWithHorse != null){
                horseInBoardList.add(NodeWithHorse.getHorse());
            }
        }
        return horseInBoardList;
    }

    /*
     * Let user pick horse to move
     * set eventListener to all horse that is able to move
     * @return hourseID
     */
    private int pickHorse() {

        return 0;
    }

    private int convertPlayerSideToView(Color color) {
        switch (color) {
            case RED: return 0;
            case GREEN: return 1;
            case BLUE: return 2;
            case YELLOW: return 3;
            default: return -1;
        }
    }


    private boolean wantToSummon() {
        //check if the user want to summon or not
        return false;
    }


    public void playGame() {

    }

    public void playNextGame() {

    }

    public void playNewGame() {

    }

    public void exit() {

    }

}
