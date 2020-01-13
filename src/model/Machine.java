package model;
import javafx.scene.shape.Path;
import model.*;

import java.util.ArrayList;

public class Machine {

    private enum PickedDice {
        DICE1, DICE2, BOTHDICE, SUMMON, REMAINDICE
    }

    private PickedDice pickedDice;
    private Board board;
    private Horse horse;

    //Constructor for 2 dice
    public Machine(Board board, Player player, Dice dice1, Dice dice2) {
        this.board = board;
        calculateMovePrecedence(dice1, dice2, player);
    }

    //Constructor for 1 dice
    public Machine(Board board, Player player, Dice dice) {
        this.board = board;
        calculateMovePrecedence(dice, player);
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


    //calculate point base on the destination in homePath
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

    //analyze horse move
    private int analyzeHorseMove(int diceValue, Player player, Horse horse, PathNode currentPosition) {
        int horsePrecedencePoint = 0;

        if (board.canMove(horse.getColor(), horse.getId(), diceValue)) {
            horsePrecedencePoint++;

            PathNode destination = board.movePathDryRun(currentPosition, diceValue);

            //Move to home path case
            if (currentPosition.isHomePosition(horse.getColor())) {
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
            if (board.calculateDistanceToHomePosition(currentPosition, player.getPlayerSide()) <= 20) {
                horsePrecedencePoint += 2;
            }
        }

        return horsePrecedencePoint;
    }

    // Calculate Move for both dice
    private void calculateMovePrecedence(Dice dice1, Dice dice2, Player player) {
        ArrayList<Horse> horseArrayList = board.findAllHorse(player);
        int maxPrecedencePoint = 0;

        for (int i = 0; i < horseArrayList.size(); i++) {
            PathNode currentPosition = board.findHorseInPath(horseArrayList.get(i).getColor(), horseArrayList.get(i).getId());
            int horsePrecedencePoint = 0;

            if (dice1.getDiceValue() == 6 || dice2.getDiceValue() == 6) {
                horsePrecedencePoint += 5;
                this.pickedDice = PickedDice.SUMMON;
            }

            //calculate precedence point based on each dice
            int dice1Point = analyzeHorseMove(dice1.getDiceValue(), player, horseArrayList.get(i), currentPosition);
            int dice2Point = analyzeHorseMove(dice2.getDiceValue(), player, horseArrayList.get(i), currentPosition);
            int bothDicePoint = analyzeHorseMove(dice1.getDiceValue() + dice2.getDiceValue(),
                    player, horseArrayList.get(i), currentPosition);

            if (dice1Point >= maxPrecedencePoint) {
                maxPrecedencePoint = dice1Point;
                setDiceAndHorse(PickedDice.DICE1, horseArrayList.get(i));
            } else if (dice2Point > maxPrecedencePoint) {
                maxPrecedencePoint = dice1Point;
                setDiceAndHorse(PickedDice.DICE2, horseArrayList.get(i));
            } else if (bothDicePoint >= maxPrecedencePoint) {
                maxPrecedencePoint = bothDicePoint;
                setDiceAndHorse(PickedDice.BOTHDICE, horseArrayList.get(i));
            }
        }
    }

    //Calculate move for the remain dice
    private void calculateMovePrecedence(Dice dice, Player player) {
        ArrayList<Horse> horseArrayList = board.findAllHorse(player);
        int maxPrecedencePoint = 0;

        for (int i = 0; i < horseArrayList.size(); i++) {
            PathNode currentPosition = board.findHorseInPath(horseArrayList.get(i).getColor(), horseArrayList.get(i).getId());
            int horsePrecedencePoint = 0;

            if (dice.getDiceValue() == 6 && board.canSummon(player.getPlayerSide())) {
                horsePrecedencePoint += 5;
                this.pickedDice = PickedDice.SUMMON;
            }

            //calculate precedence point based on each dice
            int dicePoint = analyzeHorseMove(dice.getDiceValue(), player, horseArrayList.get(i), currentPosition);

            if (dicePoint >= maxPrecedencePoint) {
                maxPrecedencePoint = dicePoint;
                setDiceAndHorse(PickedDice.REMAINDICE, horseArrayList.get(i));
            }
        }
    }
}
