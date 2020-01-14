package model;

import java.util.Random;
/**
 * Dice class is represent the dice value and a methods to throw the function
 */
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

    /**
     * Throw dice methods which generate a randome number
     */
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
