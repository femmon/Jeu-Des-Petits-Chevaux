package model;

import java.util.Random;

public class Dice {
    private int diceValue;
    private Random numberGenerator;
    private boolean pickedDice = false;

    public Dice() {
        numberGenerator = new Random();
    }

    public int getDiceValue() {
        return diceValue;
    }

    public void throwDice() {
        diceValue = numberGenerator.nextInt(6) + 1;
    }

    public boolean isPicked() {
        return this.pickedDice;
    }

    public void setPickedDice(boolean ispicked) {
        this.pickedDice = ispicked;
    }
}
