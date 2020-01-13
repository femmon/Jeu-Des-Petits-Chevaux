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
    Timeline timerThrowDiceUntilMoveAvailable;
    boolean isAnimationFinishedThrowDiceUntilMoveAvailable;
    Timeline timerThrowNewDiceAndGetInput;

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
     * @param
     */

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
        System.out.println(playerList.get(playerIndex).getPlayerSide() + " " + dice.getDiceValue());
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
        System.out.println(clickedHorsePathViewId);
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

    private int converIndexNumberForGreenSide(int number) {
        switch (number) {
            case 5:
                return 10;
            case 6:
                return 9;
            case 7:
                return 8;
            case 8:
                return 7;
            case 9:
                return 6;
            case 10:
                return 5;
            default:
                return -1;
        }
    }

    private String convertPositionToPathID(Position position) {
        String colorHexa ="";
        int num = 0;
        switch (position.getColor()) {
            case GREEN:
                colorHexa = "0x008000ff";
                if (position.getNumber() >= 0 && position.getNumber() <= 4) {
                    num = position.getNumber();
                } else if (position.getNumber() >= 5 && position.getNumber() <= 10) {
                    num = converIndexNumberForGreenSide(position.getNumber());
                } else {
                    num = position.getNumber();
                }
                return colorHexa + "_" + num;
            case BLUE:
                colorHexa = "0x0000ffff";
                return colorHexa + "_" + position.getNumber();
            case RED:
                colorHexa = "0xff0000ff";
                if (position.getNumber() >= 0 && position.getNumber() <= 5) {
                    num = position.getNumber() - 5;
                } else if (position.getNumber() >= 5 && position.getNumber() <= 10) {
                    num = 10 - position.getNumber();
                } else {
                    num = position.getNumber();
                }
                return colorHexa + "_" + num;
            case YELLOW:
                colorHexa = "0xffa500ff";
                if (position.getNumber() >= 11 && position.getNumber() <= 17) {
                    num = position.getNumber();
                } else {
                    num =  10 - position.getNumber();
                }
                return colorHexa + "_" + num;
            default:
                colorHexa = "";
        }
        return "";
    }

    private Position convertPathIDtoPosition(String pathID) {
        String[] arrOfStr = pathID.split("_", 2);
        int num =  Integer.parseInt( arrOfStr[1]);
        switch (arrOfStr[0]) {
            case "0x0000ffff":
                return new Position(Color.BLUE, num);
            case "0xffa500ff":
                if (num >= 11 && num <= 17) {
                    return new Position(Color.YELLOW, num);
                } else {
                    return new Position(Color.YELLOW, 10 - num);
                }
            case "0xff0000ff":
                int index = 0;
                if (num >= 0 && num <= 5) {
                    index = 10 - num;
                } else if (num >= 11 && num <= 17) {
                    index = num;
                } else {
                    index = num - 5;
                }
                return new Position(Color.RED, index);
            case "0x008000ff":
                int i = 0;
                if (num >= 0 && num <= 4) {
                    i = num;
                } else if (num >= 11 && num <= 17) {
                    i = num;
                } else {
                    i = converIndexNumberForGreenSide(num);
                }
                return new Position(Color.GREEN, i);
            default:
                return null;
        }
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

        timerThrowNewDiceAndGetInput = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (!isAnimationFinishedThrowDiceUntilMoveAvailable) return;

            isAnimationFinishedThrowDiceUntilMoveAvailable = false;
            if (playerList.get(turn).getPlayerType() == PlayerType.MACHINE) {
                // Display dice without button
                // Calculate move
                // Move
                // updateScore();

                // TODO: end game properly
                if (board.getIsEndGame()) {
                    timerThrowNewDiceAndGetInput.stop();
                    stopGame();
                    return;
                }

                increaseTurn();
                throwDiceUntilMoveAvailable();
            } else {
                displayOldDiceAndGetInput();
                timerThrowNewDiceAndGetInput.stop();
            }
        }));
        timerThrowNewDiceAndGetInput.setCycleCount(Timeline.INDEFINITE);
        timerThrowNewDiceAndGetInput.play();
    }

    private void increaseTurn() {
        if (dice1.getDiceValue() != dice2.getDiceValue()) {
            turn = (turn + 1) % playerList.size();
        }
    }

    private void throwDiceUntilMoveAvailable() {
        isAnimationFinishedThrowDiceUntilMoveAvailable = false;
        oneThrowDiceUntilMoveAvailable();
        timerThrowDiceUntilMoveAvailable = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            if (isMovePossible()) {
                isAnimationFinishedThrowDiceUntilMoveAvailable = true;
                timerThrowDiceUntilMoveAvailable.stop();
            } else {
                increaseTurn();
                oneThrowDiceUntilMoveAvailable();
            }
        }));
        timerThrowDiceUntilMoveAvailable.setCycleCount(Timeline.INDEFINITE);
        timerThrowDiceUntilMoveAvailable.play();
    }

    private void oneThrowDiceUntilMoveAvailable() {
        dice1 = throwDice();
        dice2 = throwDice();
        DisplayDice displayDice = new DisplayDice();
        displayDice.displayDiceWithoutBtn(dice1, dice2);
        printTurnDiceDebug();
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

        Position starting = convertPathIDtoPosition(clickedHorsePathViewId);
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

        // Not the right color or not a six
        if (color != playerList.get(turn).getPlayerSide() ||
                pickDicevalue() != 6) {
            displayOldDiceAndGetInput();
            return;
        }

        // Reroll when summon is success
        if (board.summon(color) != -1) {
            gameView.summonHorseFromNest(convertPlayerSideToView(color));
            afterSuccessfulMove();
        } else {
            displayOldDiceAndGetInput();
        }
    }

    private Color convertNestViewIdToColor(String clickedHorsePathViewId) {
        switch (clickedHorsePathViewId) {
            case "red":
                return Color.RED;
            case "green":
                return Color.GREEN;
            case "blue":
                return Color.BLUE;
            case "yellow":
                return Color.YELLOW;
            default:
                throw new IllegalArgumentException();
        }
    }

    private boolean isNestViewId(String clickedHorsePathViewId) {
        switch (clickedHorsePathViewId) {
            case "red":
            case "blue":
            case "green":
            case "yellow":
                return true;
            default:
                return false;
        }
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
