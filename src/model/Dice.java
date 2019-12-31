package model;

import java.util.Random;

public class Dice {
    private int diceValue;
    private Random numberGenerator;

    public Dice() {
        numberGenerator = new Random();
        throwDice();
    }

    public int getDiceValue() {
        return diceValue;
    }

    public void throwDice() {
        diceValue = numberGenerator.nextInt(6) + 1;
    }
}
