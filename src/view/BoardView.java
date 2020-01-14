package view;

import controller.GameController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BoardView {
    @FXML
    AnchorPane blueNest;
    @FXML
    StackPane blue_0;
    @FXML
    StackPane blue_1;

    public void initialize() {
        FileInputStream horseStream = null;
        try {
            horseStream = new FileInputStream("src/view/horsePics/BlueHorse.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image horseImage = new Image(horseStream);
        ImageView horseImageView = new ImageView(horseImage);
        horseImageView.setFitWidth(30);
        horseImageView.setFitHeight(50);

//        blue_0.getChildren().add(horseImageView);
        // Event Handler
        blue_0.setStyle("-fx-background-color: blue");
        blue_0.setOnMouseClicked(e -> {
            System.out.println("0");
            summon("");
        });
//        blue_1.getChildren().add(horseImageView);
        // Event Handler
        blue_1.setStyle("-fx-background-color: blue");
        blue_1.setOnMouseClicked(e -> {
            System.out.println("1");
            summon("");
        });
    }

    public void summon(String color) {

    }

    private ImageView getFromNest() {
        return new ImageView();
    }

    private void addToPath() {

    }

    public void move(String start, String finish) {

    }
}
