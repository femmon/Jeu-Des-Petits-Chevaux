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
import java.io.IOException;
import java.util.ArrayList;

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
    private ArrayList<ImageView> horseList = new ArrayList<>();

//    private Node[] horsePairs = new Node[2];

     public NestView(StackPane nest, int sideEnum) throws IOException {
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

    public void fillHorses() throws IOException {
        String horsePath = "src/view/horsePics/";
        switch (sideEnum) {
            case 0:
                horsePath += "RedHorse.png";
                horseStable.setId("redNest");
                horseColor = "red";
                break;
            case 1:
                horsePath += "GreenHorse.png";
                horseStable.setId("greenNest");
                horseColor = "green";
                break;
            case 2:
                horsePath += "BlueHorse.png";
                horseStable.setId("blueNest");
                horseColor = "blue";
                break;
            case 3:
                horsePath += "YellowHorse.png";
                horseStable.setId("yellowNest");
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
            horseList.add(horseResized(horseImage, horseID));
        }
        getNestContents().add(horseStable);
        horseStream.close();
    }

    private ImageView horseResized(Image horse, int horseID) {
         ImageView horseImage = new ImageView(horse);
         String id = horseColor + "_" + horseID;
         horseImage.setId(id);
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

    public ArrayList<ImageView> getHorseList() {
         return horseList;
    }

    // this method keeps track of horses in the nest


}
