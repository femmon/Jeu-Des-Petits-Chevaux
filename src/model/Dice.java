/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2019C
  Assessment: Final Project
  Created date: 14/01/2020
  By: Phuc Quang, Tran Quang, Duc, Hong, Van
  Last modified: 14/01/2020
  By: Phuc Quang, Tran Quang, Duc, Hong, Van
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/
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
