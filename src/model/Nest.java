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

import java.util.ArrayList;

/**
 * represent a nest on the board
 */
public class Nest {

    private ArrayList<Horse> horseInNest = new ArrayList<>();
    private Color color;

    public ArrayList<Horse> getHorseInNest() {
        return horseInNest;
    }

    public void setHorseInNest(ArrayList<Horse> horseInNest) {
        this.horseInNest = horseInNest;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isEmpty() {
        if (horseInNest.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void add(Horse horse) {
        horseInNest.add(horse);
    }

    public int search(Horse horse) {
        for (int i = 0; i < horseInNest.size(); i++) {
            if (horse == horseInNest.get(i)) {
                return i;
            }
        }
        return -1;
    }

    public void remove(int i) {
        horseInNest.remove(i);
    }


}
