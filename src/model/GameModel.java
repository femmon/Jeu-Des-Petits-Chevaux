package model;

import java.util.*;
import java.io.*    ;

public class GameModel {

    private boolean isEndGame = false;
    private int turn = 0;
    private ArrayList<Player> playerList = new ArrayList<Player>();
    private Board board;

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
            if (isDuplicateDiceValue(diceValue)) {
                i--;
                continue;
            }
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
//-----------------------------------GAME PLAY---------------------------------------
    public int throwDice() {
        Dice dice = new Dice();
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

    public void moveHorse(int diceValue) {

    }

    public void playGame() {
        Board gameBoard = new Board(playerList);
        int dice1 = 0;
        int dice2 = 0;

        while (!isEndGame) {
            for (int i = 0; i < playerList.size(); i++) {
                dice1 = throwDice();
                dice2 = throwDice();
            }
        }
    }
}
