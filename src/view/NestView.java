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
    private GridPane horseStable = new GridPane();
    private int sideEnum;
    private String horseColor = "";

//    private Node[] horsePairs = new Node[2];

     public NestView(StackPane nest, int sideEnum) throws FileNotFoundException {
          this.nest = nest;
          this.sideEnum = sideEnum;
          fillHorses();
      }

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
                horseColor = "red";
                break;
            case 1:
                horsePath += "GreenHorse.png";
                horseColor = "green";
                break;
            case 2:
                horsePath += "BlueHorse.png";
                horseColor = "blue";
                break;
            case 3:
                horsePath += "YellowHorse.png";
                horseColor = "yellow";
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

    public GridPane getHorseStable() {
        return horseStable;
    }

    public void addHorse(ImageView horse) {
         horseStable.getChildren().add(horse);
    }

    public int getStableSize() {
        return horseStable.getChildren().size();
    }

    // this method keeps track of horses in the nest


}
