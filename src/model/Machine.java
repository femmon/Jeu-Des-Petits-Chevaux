package model;

import model.*;
import java.util.ArrayList;

/**
 * Machine class contain the algorithm of the machine
 */

public class Machine {

    public enum PickedDice {
        DICE1, DICE2, BOTHDICE, SUMMON
    }

    private Board board;

    private PickedDice pickedDice;
    private Horse horse;

    //Constructor for 2 dice
    public Machine(Board board, Player player, Dice dice1, Dice dice2) {
        this.board = board;
        calculateMovePrecedence(dice1, dice2, player);
    }

    //get and set methods
    private void setDiceAndHorse(PickedDice dice, Horse horse) {
        this.pickedDice = dice;
        this.horse = horse;
    }

    public PickedDice getChooseDice() {
        return this.pickedDice;
    }

    public Horse getHorse() {
        return horse;
    }

    /**
     * calculate point base on the destination in homePath
     * @param destinationID
     * @return
     */
    private int calculatePointForMoveToHomePath(int destinationID) {
        switch (destinationID) {
            case 17:
                return 6;
            case 16:
                return 5;
            case 15:
                return 4;
            case 14:
                return 3;
            case 13:
                return 2;
            case 12:
                return 1;
            default:
                return 0;
        }
    }

    /**
     * analyze horse possible move with a specific dice value and calculate the precedence point
     * @param diceValue
     * @param player
     * @param horse
     * @param currentPosition
     * @return
     */
    private int analyzeHorseMove(int diceValue, Player player, Horse horse, PathNode currentPosition) {
        int horsePrecedencePoint = 0;

        if (board.canMove(player.getPlayerSide(), horse.getId(), diceValue)) {
            horsePrecedencePoint++;

            PathNode destination = board.findMoveDestination(currentPosition, diceValue);

            //Move to home path case
            if (currentPosition.isHomePosition(horse.getColor()) || board.isInHomePath(currentPosition)) {
                PathNode homePathDestination = board.moveHomePathDryRun(currentPosition, diceValue);
                if (homePathDestination != null) {
                   horsePrecedencePoint += calculatePointForMoveToHomePath(homePathDestination.getPosition().getNumber());
                }
            }

            //kicked case
            if (destination.getHorse() != null) {
                horsePrecedencePoint += 4;
            }

            //Move to home position case
            if (destination.isHomePosition(horse.getColor())) {
                horsePrecedencePoint += 3;
            }

            //Detect the horse which is near the home position
            if (!board.isInHomePath(currentPosition)) {
                if (board.calculateDistanceToHomePosition(currentPosition, player.getPlayerSide()) <= 20) {
                    horsePrecedencePoint += 2;
                }
            }
        }

        return horsePrecedencePoint;
    }

    /**
     * Calculate all the possible move of a player and decided what is the best move
     * @param dice1
     * @param dice2
     * @param player
     */
    private void calculateMovePrecedence(Dice dice1, Dice dice2, Player player) {
        ArrayList<Horse> horseArrayList = board.findAllHorse(player);
        int maxPrecedencePoint = 0;

        if (board.canSummon(player.getPlayerSide())) {
            if (dice1.getDiceValue() == 6 || dice2.getDiceValue() == 6) {
                maxPrecedencePoint += 5;
                this.pickedDice = PickedDice.SUMMON;
            }
        }

        for (int i = 0; i < horseArrayList.size(); i++) {
            PathNode currentPosition = board.findHorseInPath(horseArrayList.get(i).getColor(), horseArrayList.get(i).getId());

            //calculate precedence point based on each dice
            int dice1Point = analyzeHorseMove(dice1.getDiceValue(), player, horseArrayList.get(i), currentPosition);
            int dice2Point = analyzeHorseMove(dice2.getDiceValue(), player, horseArrayList.get(i), currentPosition);
            int bothDicePoint = analyzeHorseMove(dice1.getDiceValue() + dice2.getDiceValue(),
                    player, horseArrayList.get(i), currentPosition);

            if (dice1Point >= maxPrecedencePoint) {
                maxPrecedencePoint = dice1Point;
                setDiceAndHorse(PickedDice.DICE1, horseArrayList.get(i));
            }

            if (dice2Point > maxPrecedencePoint) {
                maxPrecedencePoint = dice2Point;
                setDiceAndHorse(PickedDice.DICE2, horseArrayList.get(i));
            }

            if (bothDicePoint >= maxPrecedencePoint) {
                maxPrecedencePoint = bothDicePoint;
                setDiceAndHorse(PickedDice.BOTHDICE, horseArrayList.get(i));
            }

            System.out.println(pickedDice);
            System.out.println("horse" + i);
            System.out.println("precedene point:" + maxPrecedencePoint);
        }
    }
}
