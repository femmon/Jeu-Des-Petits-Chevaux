package controller;

import model.*;

import java.util.ArrayList;

public class Machine {
    //Consider extends gameController

    /**
    * Point for each case in enum class
     */
    private enum CaseInGame {
        SUMMON, KICKEDHORSE, MOVETOHOMEPATH, MOVEFORWARD, MOVEINHOMEPATH,
    }

    private int pointForEachCase(CaseInGame caseInGame) {
        switch (caseInGame) {
            case SUMMON:
                return 5;
            case KICKEDHORSE:
                return 4;
            case MOVEINHOMEPATH:
                return 3;
            case MOVETOHOMEPATH:
                return 2;
            case MOVEFORWARD:
                return 1;
            default:
                return 0;
        }
    }

    /*
    *
    *   for each horse on board
    *       if canSummon
    *           horsePrecedencePoint += 5
    *       if canMove
    *       horsePrecedencePoint +=4
    *           Move destination = findDestination
    *           if move.getKickedHorse != null
    *               horsePrecedencePoint += 4
    *           if moveInHomePath
    *               horsePrecedencePoint += 3
    *
    *
     */
    private void analyzeHorseMove(Board board, GameController gameController, Dice dice1, Dice dice2, ArrayList<Horse> horseArrayList) {
        for (int i = 0; i < horseArrayList.size(); i++) {
            if (gameController.isSummon(dice1.getDiceValue(), dice2.getDiceValue())) {

            }
        }
    }

    /*
    * movePrecedence
     */
    private void calculateMovePrecedence() {
        /*
        if can summonhorse
                point += 5
        if can move
            point += 1
            if canKicked
                point += 4
            if MoveInHomePath
                point += 3
            if MoveInHomePath (distance to homepath <= 20)
                point += 2
            if MoveForward (distance to homepath > 20)
                point += 1
         */
    }

    //game play algorithm for machine

    /*
    If playerType is MACHINE
        Roll Dice
        Scan all horse in the board
        detect all available move
        Calculate the move precedence
        move base on predcedence point
        check for end game
        increase turn
     */

}
