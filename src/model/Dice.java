package model;

import java.util.Random;

public class Dice {
    private byte diceValue;
    private Random numberGenerator;

    public Dice() {
        numberGenerator = new Random();
    }

    public byte getDiceValue() {
        return diceValue;
    }

    public void throwDice() {
        diceValue = (byte) (numberGenerator.nextInt(6) + 1);
    }
}
