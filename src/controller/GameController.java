package controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.*;
import view.DisplayDice;
import view.GameView;
import view.PathView;
import view.settingController;

import java.io.IOException;
import java.util.ArrayList;

public class GameController {

    //TODO: display score board
    // playGame function
    // Machine
    // Set label for game play (button)
    // Pause game
    // Play again
    // Play new game
    // Unit test for demo
    // Network

    Stage stage;
    static GameController controller;
    GameView gameView;
    GameModel gameModel = new GameModel();
    ArrayList<ImageView> horseOnTrack = new ArrayList<>();
    private ArrayList<Player> playerList = new ArrayList<>();
    Board board;
    int turn;
    Dice dice1;
    Dice dice2;
    private boolean[] clicked = {false, false, false}; // To know if btDice1, btDice2 and btBoth were clicked.
    private String clickedHorsePathViewId = "";

    private GameController() throws IOException {
        Board board1 = new Board(playerList);
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
//        int horseId = board1.summon(playerList.get(3).getPlayerSide());
//        gameView.summonHorse(convertPlayerSideToView(playerList.get(3).getPlayerSide()));

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
            horseOnClickedEvent(destination);
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

      private void horseOnClickedEvent(String destination) {
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

//-------------------------GAME PLAY------------------------------

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
        //Testing function
        for (Player p: playerList) {
            System.out.println(p.getName() + " " + p.getPlayerType());
        }
    }

    //-----------------------Set turn---------------------
    public void rollDiceForTurn() {
        for (int i = 0; i < playerList.size(); i++) {
            Dice dice = throwDice();
            DisplayDice displayDice = new DisplayDice();
            displayDice.displayDice(dice);
            if (isDuplicateDiceValue(dice.getDiceValue())) {
                i--;
                continue;
            }
            playerList.get(i).setDiceValue(dice.getDiceValue());

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

    //--------------------Roll Dice Animation-----------------------------
    //TODO: move displayDice methods to roll dice class or game view?

    private Dice throwDice() {
        Dice dice = new Dice();
        dice.throwDice();
        return dice;
    }

    //--------------------Game play methods---------------------
    public void setClickedHorsePathViewId(String clickedHorsePathViewId) {
        this.clickedHorsePathViewId = clickedHorsePathViewId;
        if (hasDiceChosen()) {
            humanMove();
        }
    }

    private boolean hasDiceChosen() {
        for (boolean isDiceClicked: clicked) {
            if (isDiceClicked) return true;
        }
        return false;
    }

    public void setClickedDice(int option) {
        clicked = new boolean[]{false, false, false};
        clicked[option] = true;

        if (clickedHorsePathViewId.compareTo("") != 0) {
            humanMove();
        }
    }

    /**
     * receive the dice value user choose
     * @param dice1
     * @param dice2
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

    private String convertPositionToPathID(Position position) {
        String colorHexa ="";
        switch (position.getColor()) {
            case GREEN:
                colorHexa = "0x008000ff";
                break;
            case BLUE:
                colorHexa = "0x0000ffff";
                break;
            case RED:
                colorHexa = "0xff0000ff";
                break;
            case YELLOW:
                colorHexa = "0xffa500ff";
                break;
            default: colorHexa = "";
        }

        return colorHexa + "_" + position.getNumber();
    }

    private String covertHorseId(Color color, int id) {
        String colorStr = "";
        switch (color) {
            case YELLOW:
                colorStr = "yellow";
                break;
            case RED:
                colorStr = "red";
                break;
            case GREEN:
                colorStr = "green";
                break;
            case BLUE:
                colorStr = "blue";
                break;
            default:
                colorStr = " ";
                break;
        }
        return colorStr + "_" + id;
    }


    private int calculatePointInHomePath(Move destination) {
        switch (destination.getFinish().getNumber()) {
            case 12:
                return 1;
            case 13:
                return 2;
            case 14:
                return 3;
            case 15:
                return 4;
            case 16:
                return 5;
            case 17:
                return 6;
            default:
                return 0;
        }
    }

    //FIXME infinyty loop
    public void playGameOld() {
        Board board = new Board(playerList);

        while(!board.getIsEndGame()) {
            for (int i = findPlayerWithHighestDice(findMaximumDiceValue()); i < playerList.size(); i++) {
                if (playerList.get(i).getPlayerType() == PlayerType.HUMAN) {
                    boolean bonusTurn = false;
                    ArrayList<Horse> horseList;

                    //thrown dice and pick dice
                    Dice dice1 = throwDice();
                    Dice dice2 = throwDice();
                    DisplayDice displayDice = new DisplayDice();
                    displayDice.displayDice(dice1, dice2);
                    horseList = findAllHorse(playerList.get(i), board);

                    //TODO want to summon algo?
                    if (isSummon(dice1.getDiceValue(), dice2.getDiceValue())) {
                        int horseId = board.summon(playerList.get(i).getPlayerSide());
                        summonHorseFromNest(convertPlayerSideToView(playerList.get(i).getPlayerSide()));
                    }

                    if (isBonusTurn(dice1.getDiceValue(), dice2.getDiceValue())) {
                        bonusTurn = true;
                    }

                    if (horseList.size() == 0) {
                        continue;
                    }

                    if (dice1.isPicked() && dice2.isPicked()) {
                        continue;
                    } else if (dice1.isPicked() || dice2.isPicked()) {
                        int pickedHorseID = 0;
                        int move = pickDicevalue(dice1, dice2);
                        //Moving
                        Move destination = board.move(playerList.get(i).getPlayerSide(), pickedHorseID, move);
                        horseOnClickedEvent(convertPositionToPathID(destination.getFinish()));

                        //score for kicked horse
                        if (destination.getKickedHorse() != null) {
                            for (int j = 0; j < playerList.size(); j++) {
                                if (playerList.get(j).getPlayerSide() == destination.getKickedHorse().getColor()) {
                                    playerList.get(j).minusScore(2);
                                    playerList.get(i).addScore(2);
                                }
                            }
                        }
                        //score in home path
                        if (board.isMoveInMovePath(destination)) {
                            playerList.get(i).addScore(calculatePointInHomePath(destination));
                        }
                        if (board.isInHomePath(destination)) {
                            playerList.get(i).addScore(1);
                        }
                        if (board.getIsEndGame()) {
                            break;
                        }

                    }
                }
            }
        }

    }

    public void playGame() {
        board = new Board(playerList);
        board.summon(Color.RED);
        rollDiceForTurn();
        turn = findPlayerWithHighestDice(findMaximumDiceValue());

        // While instead of if to avoid recursion
        while (playerList.get(turn).getPlayerType() == PlayerType.MACHINE) {
            // AI
            break;
        }

        throwNewDiceAndGetInput();
    }

    private void throwNewDiceAndGetInput() {
        dice1 = throwDice();
        dice2 = throwDice();
        DisplayDice displayDice = new DisplayDice();
        displayDice.displayDice(dice1, dice2);
    }

    private Position pathViewIdToPosition(String pathViewId) {
        String colorCode = pathViewId.substring(0, pathViewId.length() - 2);
        String positionCode = pathViewId.substring(pathViewId.length() - 1);
        Color color;
        switch (colorCode) {
            case "0xff0000ff":
                color = Color.RED;
                break;
            case "0x008000ff":
                color = Color.GREEN;
                break;
            case "0x0000ffff":
                color = Color.BLUE;
                break;
            case "0xffa500ff":
                color = Color.YELLOW;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + pathViewId);
        }

        return new Position(color, Integer.parseInt(positionCode));
    }

    private int pickDicevalue() {
        if (clicked[0]) {
            return dice1.getDiceValue();
        } else if (clicked[1]) {
            return dice2.getDiceValue();
        } else {
            return dice1.getDiceValue() + dice2.getDiceValue();
        }
    }

    // TODO: Refactor so that can use 2 dices for 2 horses
    private void humanMove() {
        // Reset
        clickedHorsePathViewId = "";
        clicked = new boolean[]{false, false, false};

        if (isNestViewId(clickedHorsePathViewId)) {
            summon();
            return;
        }

        Position starting = pathViewIdToPosition(clickedHorsePathViewId);
        Horse horseAtStarting = board.getHorseInPosition(starting);
        // Not the right piece
        if (horseAtStarting == null ||
                horseAtStarting.getColor() != playerList.get(turn).getPlayerSide()) {
            DisplayDice displayDice = new DisplayDice();
            displayDice.displayDice(dice1, dice2);
            return;
        }

        Move destination = board.move(starting, pickDicevalue());

        // Invalid move. Prompt user to choose dice again
        if (destination.getFinish() == null) {
            DisplayDice displayDice = new DisplayDice();
            displayDice.displayDice(dice1, dice2);
            return;
        }

        // Move view

        updateScore(destination);

        if (board.getIsEndGame()) {
            stopGame();
        }

        if (dice1.getDiceValue() != dice2.getDiceValue()) {
            turn = (turn + 1) % 4;
        }

        while (playerList.get(turn).getPlayerType() == PlayerType.MACHINE) {
            // AI
            break;
        }

        throwNewDiceAndGetInput();
    }

    private void updateScore(Move destination) {
        Player player = playerList.get(turn);
        //score for kicked horse
        if (destination.getKickedHorse() != null) {
            for (int i = 0; i < playerList.size(); i++) {
                if (playerList.get(i).getPlayerSide() == destination.getKickedHorse().getColor()) {
                    playerList.get(i).minusScore(2);
                    player.addScore(2);
                }
            }
        }

        //score in home path
        if (board.isMoveInMovePath(destination)) {
            player.addScore(calculatePointInHomePath(destination));
        }
        if (board.isInHomePath(destination)) {
            player.addScore(1);
        }
    }

    // TODO: Refactor so that can use 2 dices for 2 horses
    private void summon() {
        Color color = convertNestViewIdToColor(clickedHorsePathViewId);

        // Not the right color
        if (color != playerList.get(turn).getPlayerSide()) {
            DisplayDice displayDice = new DisplayDice();
            displayDice.displayDice(dice1, dice2);
            return;
        }

        // Summon in view

        // Reroll when summon is success
        if (board.summon(color) != -1) {
            dice1 = throwDice();
            dice2 = throwDice();
        }

        DisplayDice displayDice = new DisplayDice();
        displayDice.displayDice(dice1, dice2);
    }

    private Color convertNestViewIdToColor(String clickedHorsePathViewId) {
        // Need naming rule from Hong
        return Color.RED;
    }

    private boolean isNestViewId(String clickedHorsePathViewId) {
        // Need naming rule from Hong
        return false;
    }

    // AI
        // Roll Dice
        // Display dice
        // Calculate move
        // Move
        // Check for end game
        // Increase turn

    public void stopGame() {

    }

    public void playNextGame() {

    }

    public void playNewGame() {
    }

    public void exit() {
        Platform.exit();
        System.exit(0);
    }

}
