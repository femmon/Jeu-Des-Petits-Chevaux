package model;

import java.util.ArrayList;

public class Player {

    private String name;
    private PlayerType playerType;
    private int score = 0;
    private int diceValue = 0;
    private Color playerSide;

    public Player(String name, PlayerType playerType, int score, Color playerSide) {
        this.name = name;
        this.playerType = playerType;
        this.score = score;
        this.playerSide = playerSide;
    }

    public Player(String name, PlayerType playerType, Color playerSide) {
        this.name = name;
        this.playerType = playerType;
        this.playerSide = playerSide;
    }

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

    public int getDiceValue() {
        return diceValue;
    }

    public void setDiceValue(int diceValue) {
        this.diceValue = diceValue;
    }

    public void addScore(int bonusScore) {
        this.score = score + bonusScore;
    }

    public void minusScore(int minusValue) {
        this.score = score - minusValue;
    }
}
