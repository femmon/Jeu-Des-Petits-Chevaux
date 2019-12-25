package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import model.Horse;

import java.io.IOException;

public class NestView extends Rectangle {

    @FXML StackPane blueNest;
    @FXML StackPane yellowNest;
    @FXML StackPane redNest;
    @FXML StackPane greenNest;

    public NestView() {

    }

    // this one displays new horses + delete horses
    private void addHorse(Horse horse) { System.out.println("TBA"); }

    private void removeHorse(Horse horse) {
        System.out.println("TBA");
    }

}
