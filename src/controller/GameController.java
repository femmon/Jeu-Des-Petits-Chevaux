package controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
        stage = new Stage();
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../view/pachisi.fxml")));
        Parent root = loader.load();
        gameView = new GameView((HBox) root);
        Scene primaryScene = new Scene(root, 820, 820);
        //primaryScene.getStylesheets().add(getClass().getResource("/view/debug.css").toExternalForm());
        stage.setScene(primaryScene);
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
        view.nestEvent(1);
        view.nestEvent(2);
        view.pathEvents();

        view.homePathEvent(2);
        view.homePathEvent(1);
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

    private int pickDicevalue() {
        /*
        * receive the dice value user choose
        * @return finalDiceValue
         */
        return 0;
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

    private int pickHorse() {
        /*
        *Let user pick horse to move
        * @return hourseID
         */
        return 0;
    }

    private void wantToSummon() {
        //check if the user want to summon or not
    }

    private void summonHorse() {

    }

    private void moveHorse() {

    }

    public void playGame() {

    }

    public void playNextGame() {

    }

    public void playNewGame() {

    }



}
