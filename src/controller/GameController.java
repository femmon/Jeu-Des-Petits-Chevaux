package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import view.DisplayDice;
import view.BoardView;
import view.settingView;

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
    BoardView boardView;
    private ArrayList<Player> playerList = new ArrayList<>();
    Board board;
    int turn;
    Dice dice1;
    Dice dice2;
    private boolean[] clicked = {false, false, false}; // To know if btDice1, btDice2 and btBoth were clicked.
    private String clickedHorsePathViewId = "";
    Timeline timerPlayGame;
    Timeline timerRollDiceForTurn;
    boolean isAnimationFinishedRollDiceForTurn;
    Timeline timerThrowDiceUntilMoveAvailable;
    boolean isAnimationFinishedThrowDiceUntilMoveAvailable;
    Timeline timerThrowNewDiceAndGetInput;

    private GameController() {
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../view/LanguageSettingView.fxml")));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        settingView controller = loader.getController();
        controller.initData(this);

        stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Settings");
        stage.setResizable(false);
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
        timerRollDiceForTurn = new Timeline(new KeyFrame(Duration.millis(3), event -> {
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
        displayDice.displayDiceWithoutBtn(dice, playerList.get(playerIndex).getPlayerSide().toString());
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

    private String convertPlayerSideToView(Color color) {
        switch (color) {
            case RED: return "RED";
            case GREEN: return "GREEN";
            case BLUE: return "BLUE";
            case YELLOW: return "YELLOW";
            default: return "";
        }
    }

    private boolean wantToSummon() {
        //check if the user want to summon or not
        return false;
    }


    private String convertPositionToPathID(Position position) {
        return position.getColor() + "_" + position.getNumber();
    }

    private Position convertPathIDtoPosition(String pathID) {
        String[] arr = pathID.split("_", 2);
        switch (arr[0]) {
            case "RED":
                return new Position(Color.RED, Integer.parseInt(arr[1]));
            case "GREEN":
                return new Position(Color.GREEN, Integer.parseInt(arr[1]));
            case "YELLOW":
                return new Position(Color.YELLOW, Integer.parseInt(arr[1]));
            case "BLUE":
                return new Position(Color.BLUE, Integer.parseInt(arr[1]));
            default:
                throw new IllegalArgumentException();
        }
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
        resetTimeline();
        // Create game view
        FXMLLoader boardLoader = new FXMLLoader((getClass().getResource("../view/BoardView.fxml")));
        StackPane boardViewStackPane = null;
        try {
            boardViewStackPane = boardLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        boardView = boardLoader.getController();

        Scene scene = new Scene(boardViewStackPane);
        stage.setScene(scene);
        stage.setTitle("Playing");

        board = new Board(playerList);
        rollDiceForTurn();

        timerPlayGame = new Timeline(new KeyFrame(Duration.millis(1), event -> {
            if (isAnimationFinishedRollDiceForTurn) {
                // Reset
                isAnimationFinishedRollDiceForTurn = false;
                timerPlayGame.stop();
                turn = findPlayerWithHighestDice(findMaximumDiceValue());

                throwNewDiceAndGetInput();
            }
        }));
        timerPlayGame.setCycleCount(Timeline.INDEFINITE);
        timerPlayGame.play();
    }

    private void resetTimeline() {
        if (timerPlayGame != null) timerPlayGame.stop();
        if (timerRollDiceForTurn != null) timerRollDiceForTurn.stop();
        if (timerThrowDiceUntilMoveAvailable != null) timerThrowDiceUntilMoveAvailable.stop();
        if (timerThrowNewDiceAndGetInput != null) timerThrowNewDiceAndGetInput.stop();
        isAnimationFinishedRollDiceForTurn = false;
        isAnimationFinishedThrowDiceUntilMoveAvailable = false;
    }

    /**
     * Throw dice, move AI until reach human player
     */
    private void throwNewDiceAndGetInput() {
        throwDiceUntilMoveAvailable();

        timerThrowNewDiceAndGetInput = new Timeline(new KeyFrame(Duration.millis(1), event -> {
            if (!isAnimationFinishedThrowDiceUntilMoveAvailable) return;

            isAnimationFinishedThrowDiceUntilMoveAvailable = false;
            if (playerList.get(turn).getPlayerType() == PlayerType.MACHINE) {
                // Display dice without button
                DisplayDice displayDice = new DisplayDice();
                displayDice.displayDiceWithoutBtn(dice1, dice2, playerList.get(turn).getPlayerSide().toString());

                // Move
                int diceValue = 0;
                Machine machine = new Machine(board, playerList.get(turn), dice1, dice2);
                switch (machine.getChooseDice()) {
                    case DICE1:
                        diceValue += dice1.getDiceValue();
                        break;
                    case DICE2:
                        diceValue += dice2.getDiceValue();
                        break;
                    case BOTHDICE:
                        diceValue += dice1.getDiceValue();
                        diceValue += dice2.getDiceValue();
                        break;
                    case SUMMON:
                        board.summon(playerList.get(turn).getPlayerSide());
                        boardView.summon(playerList.get(turn).getPlayerSide().toString());
                        break;
                }

                if (diceValue != 0) {
                    Horse horse = machine.getHorse();
                    Move destination = board.move(horse.getColor(), horse.getId(), diceValue);

                    String destinationViewPathId = convertPositionToPathID(destination.getFinish());
                    boardView.move(convertPositionToPathID(destination.getStart()), destinationViewPathId);

                    updateScore(destination);
                }

                // TODO: end game properly
                if (board.getIsEndGame()) {
                    timerThrowNewDiceAndGetInput.stop();
                    setEndGame();
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
        timerThrowDiceUntilMoveAvailable = new Timeline(new KeyFrame(Duration.millis(3), event -> {
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
        displayDice.displayDiceWithoutBtn(dice1, dice2,playerList.get(turn).getPlayerSide().toString());
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
        displayDice.displayDice(dice1, dice2, playerList.get(turn).getPlayerSide().toString());
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
        boardView.move(convertPositionToPathID(destination.getStart()), destinationViewPathId);

        updateScore(destination);

        if (board.getIsEndGame()) {
            setEndGame();
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
            boardView.summon(convertPlayerSideToView(color));
            afterSuccessfulMove();
        } else {
            displayOldDiceAndGetInput();
        }
    }

    private Color convertNestViewIdToColor(String clickedHorsePathViewId) {
        switch (clickedHorsePathViewId) {
            case "RED":
                return Color.RED;
            case "GREEN":
                return Color.GREEN;
            case "BLUE":
                return Color.BLUE;
            case "YELLOW":
                return Color.YELLOW;
            default:
                throw new IllegalArgumentException();
        }
    }

    private boolean isNestViewId(String clickedHorsePathViewId) {
        switch (clickedHorsePathViewId) {
            case "RED":
            case "BLUE":
            case "GREEN":
            case "YELLOW":
                return true;
            default:
                return false;
        }
    }

    public void setEndGame() {
        //Appear leaderboard
    }

    public void playAgain() {
        //Keep playerList and Score
    }

    public void playNewGame() {
        //Reset Board
        //Reset player and its score
    }

    public void exit() {
        Platform.exit();
        System.exit(0);
    }

}
