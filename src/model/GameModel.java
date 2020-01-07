//package model;
//
//import javafx.scene.shape.Path;
//
//import java.util.*;
//import java.io.*    ;
//
//public class GameModel {
//
//    private boolean isEndGame = false;
//    private boolean bonusTurn = false;
//    private int turn = 0;
//    private ArrayList<Player> playerList = new ArrayList<Player>();
////    private Board board;
//
////---------------------------------SET PLAYER----------------------------------------------
//
//    public void setPlayer(String name, PlayerType playerType, Color color) {
//        Player player = new Player(name, playerType, color);
//        this.playerList.add(player);
//    }
//
//    public ArrayList<Player> getPlayerList() {
//        return playerList;
//    }
//
////---------------------------------SET TURN ORDER-------------------------------------------
//
//    public void rollDiceForTurn() {
//        int tempDice = 0;
//        for (int i = 0; i < playerList.size(); i++) {
//            int diceValue = throwDice();
//            if (isDuplicateDiceValue(diceValue)) {
//                i--;
//                continue;
//            }
//            playerList.get(i).setDiceValue(diceValue);
//
//        }
//    }
//
//    private boolean isDuplicateDiceValue(int diceValue) {
//        for (int i = 0; i < playerList.size(); i++) {
//            if (diceValue == playerList.get(i).getDiceValue()) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private int findMaximumDiceValue() {
//        int max = 0;
//        for (Player player : playerList) {
//            if (player.getDiceValue() > max) {
//                max = player.getDiceValue();
//            }
//        }
//        return max;
//    }
//
//    private int findPlayerWithHighestDice(int max) {
//        for (int i = 0; i < playerList.size(); i++) {
//            if (playerList.get(i).getDiceValue() == max) {
//                return i;
//            }
//        }
//        return -1;
//    }
////-----------------------------------GAME ACTION---------------------------------------
//    public int throwDice() {
//        Dice dice = new Dice();
//        dice.throwDice();
//        return dice.getDiceValue();
//    }
//
//    public boolean isSummon(int dice1, int dice2) {
//        return dice1 == 6 || dice2 == 6;
//    }
//
//    public boolean isBonusTurn(int dice1, int dice2) {
//        return dice1 == dice2;
//    }
//
//    public void chooseDice(int diceValue, Player player) {
//        player.setDiceValue(diceValue);
//    }
//
//    public ArrayList<Horse> findAllHorse(Player player, Board board) {
//        ArrayList<Horse> horseInBoardList = new ArrayList<Horse>();
//
//        for (int i = 0; i < 4; i++) {
//            PathNode NodeWithHorse = board.findHorseInPath(player.getPlayerSide(), i);
//            if (NodeWithHorse != null){
//                horseInBoardList.add(NodeWithHorse.getHorse());
//            }
//        }
//        return horseInBoardList;
//    }
//
//    public Horse chooseHorse(ArrayList<Horse> horseArrayList, int id) {
//        return horseArrayList.get(id);
//    }
//
//    public int calculatePointInHomePath(PathNode destination) {
//        switch (destination.getPosition().getNumber()) {
//            case 12:
//                return 1;
//            case 13:
//                return 2;
//            case 14:
//                return 3;
//            case 15:
//                return 4;
//            case 16:
//                return 5;
//            case 17:
//                return 6;
//            default:
//                return 0;
//        }
//    }
//
//    public boolean isInHomePath(PathNode currentPosition) {
//        return currentPosition.getPosition().getNumber() >= 12
//                && currentPosition.getPosition().getNumber() <= 17;
//    }
//
//    //---------------------------------------TEST-----------------------------------------------------------------
//    public void initPlayer() {
//        setPlayer("1", PlayerType.HUMAN, Color.BLUE);
//        setPlayer("2", PlayerType.MACHINE, Color.YELLOW);
//        setPlayer("3", PlayerType.HUMAN, Color.GREEN);
//        setPlayer("4", PlayerType.MACHINE, Color.RED);
//
//    }
//
//    private void printTurnOrder() {
//        int i = 0;
//        for (Player player : playerList) {
//            System.out.println(i + "- Dice Value:" + playerList.get(i).getDiceValue() +
//                    " Player Side:" + playerList.get(i).getPlayerSide());
//            i++;
//        }
//    }
//
//    private int pickDiceValue(int Dice1, int Dice2) {
//        System.out.println("CHOOSE DICE");
//        System.out.println("1 - Dice1, 2 - Dice2, 3 - both Dice: ");
//        Scanner input = new Scanner(System.in);
//        int i = input.nextInt();
//        if (i == 1) return Dice1;
//        else if (i == 2) return Dice2;
//        else return Dice1 + Dice2;
//    }
//
//    private boolean wantToSummon() {
//        System.out.println("Enter number 1 if want to summon (1 - Y/ 2 - N):");
//        Scanner input = new Scanner(System.in);
//        int i = input.nextInt();
//        if (i == 1) return true;
//        else if (i == 2) return false;
//        else return false;
//    }
//
//    private void printHorseList(ArrayList<Horse> horseList) {
//        System.out.println("horse ID list:");
//        int i = 0;
//        for (Horse horse : horseList) {
//            System.out.println(horseList.get(i).getId());
//            i++;
//        }
//    }
//
//    private int pickHorse() {
//        System.out.println("enter horse ID: ");
//        Scanner input = new Scanner(System.in);
//        return input.nextInt();
//    }
//
//    public void playGame() {
//        initPlayer();
//        rollDiceForTurn();
//        System.out.println("START GAME");
//        Board board = new Board(playerList);
//        int dice1 = 0;
//        int dice2 = 0;
//        System.out.println("-------------------TurnOrder-------------------");
//        printTurnOrder();
//        while (!board.getIsEndGame()) {
//            for (int i = findPlayerWithHighestDice(findMaximumDiceValue()); i < playerList.size(); i++) {
//
//                System.out.println("--------------------Turn-------------------");
//                System.out.println("Turn: Player " + playerList.get(i).getPlayerSide());
//                bonusTurn = false;
//                ArrayList<Horse> horseList;
//
//                //throw dice
//                dice1 = throwDice();
//                dice2 = throwDice();
//                System.out.println("Dice 1 Value:" + dice1 );
//                System.out.println("Dice 2 Value:" + dice2 );
//
//                //check for summon condition and bonus turn
//                if (isSummon(dice1, dice2)) {
//                    if (wantToSummon()) {
//                        if (board.summon(playerList.get(i).getPlayerSide()) != -1) {
//                            System.out.println("summon success");
//                            continue;
//                        } else {
//                            System.out.println("summon failed");
//                        }
//                    }
//                }
//
//                //Check bonus turn
//                if (isBonusTurn(dice1, dice2)) {
//                    bonusTurn = true;
//                }
//
//                //Scan all horse in the board
//                horseList = findAllHorse(playerList.get(i), board);
//
//                if (horseList.size() != 0) {
//                    //pick dice value
//                    int moves = pickDiceValue(dice1, dice2);
//                    printHorseList(horseList);
//
//                    //pick Horse
//                    int pickedHorseID = pickHorse();
//
//                    //Move and score
//                    PathNode currentPosition =
//                            board.findHorseInPath(playerList.get(i).getPlayerSide(), pickedHorseID);
//
//                    // FIXME: can be NULL
//                    PathNode destination =
//                            board.findMoveDestination(playerList.get(i).getPlayerSide(), pickedHorseID, moves);
//
//                    Horse kickedHorse = board.kickHorse(destination);
//
//                    // Add bonus score for kick horse
//                    if (kickedHorse != null) {
//                        for (int j = 0; j < playerList.size(); j++) {
//                            if (playerList.get(j).getPlayerSide() == kickedHorse.getColor()) {
//                                playerList.get(j).minusScore(2);
//                                playerList.get(i).addScore(2);
//                            }
//                        }
//                    }
//                    //Move
//                    board.moveHorse(pickedHorseID,playerList.get(i).getPlayerSide(), destination);
//
//                    //Add score if move to HomePath
//                    if (board.isMoveInHomePath(currentPosition)) {
//                        playerList.get(i).addScore(calculatePointInHomePath(destination));
//                    }
//
//                    if (isInHomePath(currentPosition)) {
//                        playerList.get(i).addScore(1);
//                    }
//                    //Check for endGame
//                    board.checkForEndGameFlag(destination);
//
//                    if (board.getIsEndGame()) {
//                        break;
//                    }
//                } else {
//                    System.out.println("No horse End turn");
//                }
//
//                if (bonusTurn) {
//                    i--;
//                }
//            }
//        }
//
//    }
//}
