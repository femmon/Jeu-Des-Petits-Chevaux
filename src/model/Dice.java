package model;

import java.util.Random;

public class Dice {
    private int dice1Value;
    private int dice2Value;
    private Random numberGenerator;

    public Dice() {
        numberGenerator = new Random();
        throwDice();
    }

    public int getDice1Value() {
        return dice1Value;
    }

    public int getDice2Value() {
        return dice2Value;
    }

    public void throwDice() {
        dice1Value = numberGenerator.nextInt(6) + 1;
        dice2Value = numberGenerator.nextInt(6) + 1;
    }
}
