package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import view.DisplayDice;
import view.GameView;
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

    //FIXME to much global ???
    Stage stage;
    static GameController controller;
    GameView gameView;
    private ArrayList<Player> playerList = new ArrayList<>();
    Board board;
    int turn;
    Dice dice1;
    Dice dice2;
    private boolean[] clicked = {false, false, false}; // To know if btDice1, btDice2 and btBoth were clicked.
    private String clickedHorsePathViewId = "";
    Timeline timerRollDiceForTurn;
    boolean isAnimationFinishedRollDiceForTurn;

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

//        controllerDemo(gameView);

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
//        playGame();
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
                continue;
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
        isAnimationFinishedRollDiceForTurn = false;

        oneRollDiceForTurn();
        timerRollDiceForTurn = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            oneRollDiceForTurn();
        }));
        timerRollDiceForTurn.setCycleCount(Timeline.INDEFINITE);
        timerRollDiceForTurn.play();
    }

    private void oneRollDiceForTurn() {
        int playerIndex = getCurrentRollDiceForTurnPlayer();
        if (playerIndex == -1) {
            isAnimationFinishedRollDiceForTurn = true;
            timerRollDiceForTurn.stop();
            return;
        }

        Dice dice = throwDice();
        DisplayDice displayDice = new DisplayDice();
        displayDice.displayDice(dice);

        if (!isDuplicateDiceValue(dice.getDiceValue())) {
            playerList.get(playerIndex).setDiceValue(dice.getDiceValue());
        }
    }

    private int getCurrentRollDiceForTurnPlayer() {
        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i).getDiceValue() == 0) return i;
        }

        return -1;
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

    public void playGame() {
        board = new Board(playerList);
        rollDiceForTurn();

        Timeline timerPlayGame = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (isAnimationFinishedRollDiceForTurn) {
                // Reset
                isAnimationFinishedRollDiceForTurn = false;
                // TODO: How to kill TimeLine from
//                timerPlayGame
                turn = findPlayerWithHighestDice(findMaximumDiceValue());

                throwNewDiceAndGetInput();
            }
        }));
        timerPlayGame.setCycleCount(Timeline.INDEFINITE);
        timerPlayGame.play();
    }

    /**
     * Throw dice, move AI until reach human player
     */
    private void throwNewDiceAndGetInput() {
        throwDiceUntilMoveAvailable();

        // While instead of if to avoid recursion
        while (playerList.get(turn).getPlayerType() == PlayerType.MACHINE) {
            // Display dice without button
            // Calculate move
            // Move
            // updateScore();

            increaseTurn();
            throwDiceUntilMoveAvailable();

            // TODO: end game properly
            if (board.getIsEndGame()) {
                stopGame();
            }
        }

        displayOldDiceAndGetInput();
    }

    private void increaseTurn() {
        if (dice1.getDiceValue() != dice2.getDiceValue()) {
            turn = (turn + 1) % playerList.size();
        }
    }

    private void throwDiceUntilMoveAvailable() {
        dice1 = throwDice();
        dice2 = throwDice();
        printTurnDiceDebug();

        while (!isMovePossible()) {
            // TODO display dice without button
            increaseTurn();
            dice1 = throwDice();
            dice2 = throwDice();
            printTurnDiceDebug();
        }
    }

    void printTurnDiceDebug() {
        Player player = playerList.get(turn);
        System.out.println(player.getPlayerSide() + " - " + player.getName() + ": " +
                dice1.getDiceValue() + " " + dice2.getDiceValue());
    }

    private boolean isMovePossible() {
        boolean hasASix = dice1.getDiceValue() == 6 || dice2.getDiceValue() == 6;
        Color playerColor = playerList.get(turn).getPlayerSide();
        if (hasASix && board.canSummon(playerColor)) {
            return true;
        }

        int[] diceValues = {dice1.getDiceValue(), dice2.getDiceValue()};
        for (int diceValue: diceValues) {
            for (int horseId = 0; horseId < 4; horseId++) {
                if (board.canMove(playerColor, horseId, diceValue)) return true;
            }
        }

        return false;
    }

    private void displayOldDiceAndGetInput() {
        // Reset
        clickedHorsePathViewId = "";
        clicked = new boolean[]{false, false, false};

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
        if (isNestViewId(clickedHorsePathViewId)) {
            summon();
            return;
        }

        Position starting = pathViewIdToPosition(clickedHorsePathViewId);
        Horse horseAtStarting = board.getHorseInPosition(starting);
        // Not the right piece
        if (horseAtStarting == null ||
                horseAtStarting.getColor() != playerList.get(turn).getPlayerSide()) {
            displayOldDiceAndGetInput();
            return;
        }

        Move destination = board.move(starting, pickDicevalue());

        // Invalid move. Prompt user to choose dice again
        if (destination.getFinish() == null) {
            displayOldDiceAndGetInput();
            return;
        }

        String destinationViewPathId = convertPositionToPathID(destination.getFinish());
        if (destination.getKickedHorse() != null) {
            gameView.removeHorse(destinationViewPathId);
        }
        gameView.moveHorse(convertPositionToPathID(destination.getStart()), destinationViewPathId);

        updateScore(destination);

        if (board.getIsEndGame()) {
            stopGame();
        }

        afterSuccessfulMove();
    }

    private void afterSuccessfulMove() {
        increaseTurn();
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
            displayOldDiceAndGetInput();
            return;
        }

        gameView.summonHorseFromNest(convertPlayerSideToView(color));

        // Reroll when summon is success
        if (board.summon(color) != -1) {
            afterSuccessfulMove();
        } else {
            displayOldDiceAndGetInput();
        }
    }

    private Color convertNestViewIdToColor(String clickedHorsePathViewId) {
        // Need naming rule from Hong
        return Color.RED;
    }

    private boolean isNestViewId(String clickedHorsePathViewId) {
        // Need naming rule from Hong
        return false;
    }

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
