package model;

import java.util.ArrayList;

public class Player {

    private String name;
    private PlayerType playerType;
    private int score = 0;
    private Color playerSide;
    private ArrayList<Horse> horseList= new ArrayList<Horse>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Color getPlayerSide() {
        return playerSide;
    }

    public void setPlayerSide(Color playerSide) {
        this.playerSide = playerSide;
    }

    public ArrayList<Horse> getHorseList() {
        return horseList;
    }

    public void setHorseList(ArrayList<Horse> horseList) {
        this.horseList = horseList;
    }

    public Horse getHorse(int i) {
        return horseList.get(i);
    }

    public void addHorse(Horse horse) {
        horseList.add(horse);
    }

    public void removeHorse(int i){
        horseList.remove(i);
    }
}
