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

/**
 * NestView fills horses into the nest and listens instructions from GameView
 * NestView is the only View subclass which handles events
 * Parent: GameView
 */

public class NestView {
    private StackPane nest;
    GridPane horseStable = new GridPane();
    private int sideEnum;
    private int horseClickedState = 0;
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
                break;
            case 1:
                horsePath += "GreenHorse.png";
                horseStable.setId("greenCage");
                break;
            case 2:
                horsePath += "BlueHorse.png";
                horseStable.setId("blueCage");
                break;
            case 3:
                horsePath += "YellowHorse.png";
                horseStable.setId("yellowCage");
                break;
        }

        FileInputStream horseStream = new FileInputStream(horsePath);
        Image horseImage = new Image(horseStream);

        horseStable.setAlignment(Pos.CENTER);
        horseStable.setVgap(5);
        horseStable.setHgap(5);

        for (int horseID = 0; horseID <= 3; horseID++) {
            horseStable.add(horseResized(horseImage), flipFlopping(horseID), horseID / 2);
        }
        getNestContents().add(horseStable);

        // demoing

//        removeHorse("blueCage");
//        removeHorse("yellowCage");
//        removeHorse("greenCage");
    }

    private ImageView horseResized(Image horse) {
         ImageView horseImage = new ImageView(horse);
         horseImage.setFitWidth(36);
         horseImage.setFitHeight(60);
         return horseImage;
    }


    // event 2. remove horse - PASS
    /**
    * Remember that this function only removes the most recent horse ID that is called
     */
    public void removeHorse() {
        horseStable.setOnMouseClicked(e -> horseStable.getChildren().remove(0, 1));
    }

    // event 3. put horse to new position

}
