package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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

    private ArrayList<Player> playerList = new ArrayList<Player>();

    private GameController() throws IOException {
        // Create game view
        HBox board = (HBox) FXMLLoader.load(getClass().getResource("../view/pachisi.fxml"));
        gameView = new GameView(board);

        // Create setting controller and pass in board to display after setting
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../view/LanguageSettingView.fxml")));
        Parent root = loader.load();
        settingController controller = (settingController) loader.getController();
        controller.setBoard(board);

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
    private void controllerDemo(GameView view) {
        view.pathEvents();
        view.homePathEvent(2);
        view.homePathEvent(1);
        gameView.summonHorse(3);
        moveHorse("0xffa500ff_2");
        System.out.println(gameView.exportChosenHorseID());
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

    private void throwDice() {
        /*
        * get dice value
        * Display dice (Roll animation)
        */
        Dice dice = new Dice();
        dice.throwDice();

    }

    public boolean isSummon(int dice1, int dice2) {
        return dice1 == 6 || dice2 == 6;
    }

    public boolean isBonusTurn(int dice1, int dice2) {
        return dice1 == dice2;
    }

    //-----------------------Set player----------------------------

    public void setPlayer(String name, PlayerType playerType, Color color) {
        Player player = new Player(name, playerType, color);
        this.playerList.add(player);
    }

    private void setPlayerList() {
        /*
         * Receive input from the setting and setplayer
         */
    }

    //-----------------------Set turn---------------------
    private void setTurn() {
        /*
        * set turn base on the dice value
         */
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


    private void moveHorse(String id) {
        // 2 for loops
        // looping all paths
        // looping each element of path
        PathView[] pathGroup = gameView.getAllPaths();
        for (PathView pathView : pathGroup) {
            ObservableList<Node> pathContents = pathView.getPathContents();
            for (Node pathContent : pathContents) {
                StackPane candidateNode = (StackPane) pathContent;
                if (pathContent.getId().equals(id)) {
                    Label label = new Label("Bruh");
                    candidateNode.getChildren().add(label);
                    label.toFront();
                    System.out.println("Id found");
                }
            }
        }
    }

//    private void summonHorse(GameView view, int index) {
//        view.summonHorse(index);
//    }

    public void playGame() {

    }

    public void playNextGame() {

    }

    public void playNewGame() {

    }

    public void exit() {

    }

}
