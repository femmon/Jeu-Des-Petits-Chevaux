package view;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * NestView fills horses into the nest and listens instructions from GameView
 * NestView is the only View subclass which handles events
 * Parent: GameView
 */

public class NestView {
    private StackPane nest;
    GridPane horseStable = new GridPane();
    private int sideEnum;
    private String horseColor = "";
    private ArrayList<ImageView> removedHorses;

//    private Node[] horsePairs = new Node[2];

     public NestView(StackPane nest, int sideEnum) throws FileNotFoundException {
          this.nest = nest;
          this.sideEnum = sideEnum;
          fillHorses();
      }

//    public void getHorseCount() {
//        System.out.println("horseCount = " + horseCount);
//    }

    private int flipFlopping (int i) {
        if (i % 2 == 0) return 0;
        else return 1;
    }

    public ObservableList<Node> getNestContents() {
        return nest.getChildren();
    }

    public void fillHorses() throws FileNotFoundException {
        String horsePath = "src/view/horsePics/";
        switch (sideEnum) {
            case 0:
                horsePath += "RedHorse.png";
                horseStable.setId("redCage");
                horseColor = "Red";
                break;
            case 1:
                horsePath += "GreenHorse.png";
                horseStable.setId("greenCage");
                horseColor = "Green";
                break;
            case 2:
                horsePath += "BlueHorse.png";
                horseStable.setId("blueCage");
                horseColor = "Blue";
                break;
            case 3:
                horsePath += "YellowHorse.png";
                horseStable.setId("yellowCage");
                horseColor = "Yellow";
                break;
        }

        FileInputStream horseStream = new FileInputStream(horsePath);
        Image horseImage = new Image(horseStream);

        horseStable.setAlignment(Pos.CENTER);
        horseStable.setVgap(5);
        horseStable.setHgap(5);

        for (int horseID = 0; horseID <= 3; horseID++) {
            horseStable.add(horseResized(horseImage, horseID), flipFlopping(horseID), horseID / 2);
        }
        getNestContents().add(horseStable);

        // demoing

//        removeHorse("blueCage");
//        removeHorse("yellowCage");
//        removeHorse("greenCage");
    }

    private ImageView horseResized(Image horse, int horseID) {
         ImageView horseImage = new ImageView(horse);
         horseImage.setId(horseColor + "_" + horseID);
         horseImage.setFitWidth(30);
         horseImage.setFitHeight(40);
         return horseImage;
    }


    // event 2. remove horse - PASS
    /**
    * Remember that this function only removes the most recent horse ID that is called
     */
    public void addToRemovedList(ImageView horse) {
        removedHorses.add(horse);
    }

    public ArrayList<ImageView> getRemovedHorses() {
        return removedHorses;
    }



    // event 3. put horse to new position

}
