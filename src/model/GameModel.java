package model;

import java.util.*;
import java.io.*    ;

public class GameModel {

    private boolean isEndGame = false;
    private boolean bonusTurn = false;
    private int turn = 0;
    private ArrayList<Player> playerList = new ArrayList<Player>();
//    private Board board;

//---------------------------------SET PLAYER----------------------------------------------

    public void setPlayer(String name, PlayerType playerType, Color color) {
        Player player = new Player(name, playerType, color);
        this.playerList.add(player);
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

//---------------------------------SET TURN ORDER-------------------------------------------

    public void setTurnOrder() {
        //roll dice for set turn
        int tempDice = -1;
        for (int i = 0; i < playerList.size(); i++) {
            int diceValue = throwDice();
//            if (isDuplicateDiceValue(diceValue)) {
//                i--;
//                continue;
//            }
            playerList.get(i).setDiceValue(diceValue);
        }
        sortPlayerDiceValue();
    }

    public boolean isDuplicateDiceValue(int diceValue) {
        int i = 0;
        for (Player player : playerList) {
            if (playerList.get(i).getDiceValue() == diceValue) {
                return true;
            }
            i++;
        }
        return false;
    }

    public void sortPlayerDiceValue() {
        //sort player dice
        Comparator<Player> playerDiceValue = new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                return Integer.compare(p1.getDiceValue(), p2.getDiceValue());
            }
        };
        Collections.sort(this.playerList, playerDiceValue);
        Collections.reverse(playerList);
    }
//-----------------------------------GAME ACTION---------------------------------------
    public int throwDice() {
        Dice dice = new Dice();
        dice.throwDice();
        return dice.getDiceValue();
    }

    public boolean isSummon(int dice1, int dice2) {
        return dice1 == 6 || dice2 == 6;
    }

    public boolean isBonusTurn(int dice1, int dice2) {
        return dice1 == dice2;
    }

    public void chooseDice(int diceValue, Player player) {
        player.setDiceValue(diceValue);
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

    public Horse chooseHorse(ArrayList<Horse> horseArrayList, int id) {
        return horseArrayList.get(id);
    }

    //---------------------------------------TEST-----------------------------------------------------------------
    public void initPlayer() {
        setPlayer("1", PlayerType.HUMAN, Color.BLUE);
        setPlayer("2", PlayerType.MACHINE, Color.RED);
        setPlayer("3", PlayerType.HUMAN, Color.GREEN);
        setPlayer("4", PlayerType.MACHINE, Color.YELLOW);
    }

    private void printTurnOrder() {
        int i = 0;
        for (Player player : playerList) {
            System.out.println(i + "-" + playerList.get(i).getName() + " " + playerList.get(i).getPlayerSide());
            i++;
        }
    }

    private int pickDiceValue(int Dice1, int Dice2) {
        System.out.println("CHOOSE DICE");
        System.out.println("1 - Dice1, 2 - Dice2, 3 - both Dice: ");
        Scanner input = new Scanner(System.in);
        int i = input.nextInt();
        if (i == 1) return Dice1;
        else if (i == 2) return Dice2;
        else return Dice1 + Dice2;
    }

    private boolean wantToSummon() {
        System.out.println("Enter number 1 if want to summon (1 - Y/ 2 - N):");
        Scanner input = new Scanner(System.in);
        int i = input.nextInt();
        if (i == 1) return true;
        else if (i == 2) return false;
        else return false;
    }

    private void printHorseList(ArrayList<Horse> horseList) {
        System.out.println("horse ID list:");
        int i = 0;
        for (Horse horse : horseList) {
            System.out.println(horseList.get(i).getId());
            i++;
        }
    }

    private int pickHorse() {
        System.out.println("enter horse ID: ");
        Scanner input = new Scanner(System.in);
        return input.nextInt();
    }

    public void playGame() {
        //initPlayer();
        System.out.println("START GAME");
        Board board = new Board(playerList);
        int dice1 = 0;
        int dice2 = 0;
        setTurnOrder();
        System.out.println("-------------------TurnOrder-------------------");
        printTurnOrder();
        while (!isEndGame) {
            for (int i = 0; i < playerList.size(); i++) {
                System.out.println("--------------------Turn-------------------");
                System.out.println("Turn: Player " + playerList.get(i).getPlayerSide());
                //init variable
                bonusTurn = false;
                ArrayList<Horse> horseList;
                //throw dice
                dice1 = throwDice();
                dice2 = throwDice();
                System.out.println("Dice1 Value:" + dice1 );
                System.out.println("Dice2 Value:" + dice2 );
                //check for summon condition and bonus turn
                if (isSummon(dice1, dice2)) {
                    bonusTurn = true;
                    if (wantToSummon()) {
                        if (board.summon(playerList.get(i).getPlayerSide())) {
                            System.out.println("summon success");
                        } else {
                            System.out.println("summon failed");
                        }
                    }
                } else if (isBonusTurn(dice1, dice2)) {
                    bonusTurn = true;
                }
                //move
                horseList = findAllHorse(playerList.get(i), board);
                if (horseList.size() != 0) {
                    int moves = pickDiceValue(dice1, dice2);
                    printHorseList(horseList);
                    int pickedHorseID = pickHorse();
                    Position pos = board.move(horseList.get(pickedHorseID).getColor(), pickedHorseID, moves);
                    System.out.println("Horse move to position: " + pos.getColor() + "-" + pos.getNumber());
                } else {
                    System.out.println("No horse End turn");
                }

                if (bonusTurn) {
                    i--;
                }
            }
        }
    }
}
