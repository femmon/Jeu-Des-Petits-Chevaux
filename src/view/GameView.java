package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class GameView {


    @FXML StackPane blueNest;
    @FXML StackPane yellowNest;
    @FXML StackPane redNest;
    @FXML StackPane greenNest;

    @FXML VBox home_path_blue;
    @FXML VBox home_path_green;
    @FXML HBox home_path_yellow;
    @FXML HBox home_path_red;

    // obtains scene from the fxml
    public GameView(GridPane pachisi) {
        fetchingNests(pachisi);
        fetchingHomePath(pachisi);
    }

    public void fetchingNests(GridPane pachisi) {
        blueNest = (StackPane) pachisi.lookup("#blueNest");
        yellowNest = (StackPane) pachisi.lookup("#yellowNest");
        redNest = (StackPane) pachisi.lookup("#redNest");
        greenNest = (StackPane) pachisi.lookup("#greenNest");
        getContentsOf(blueNest);
        getContentsOf(redNest);
        getContentsOf(yellowNest);
        getContentsOf(greenNest);
    }

    private void fetchingHomePath(GridPane pachisi) {
        home_path_blue = (VBox) pachisi.lookup("#home_path_blue");
        home_path_green = (VBox) pachisi.lookup("#home_path_green");
        home_path_yellow = (HBox) pachisi.lookup("#home_path_yellow");
        home_path_red = (HBox) pachisi.lookup("#home_path_red");


    }

    public void getContentsOf(StackPane nest) {
        NestView selectedNest = new NestView(nest);
        System.out.println(nest.getId() + " : " + selectedNest.getNestContents());
    }

//        public void verticalPathLabeling(VBox vPath) {
//            for (int index = 1; index < 6; ++index) {
//                StackPane realHomePath = new StackPane();
//                realHomePath.getChildren().addAll(vPath.getChildren().get(index), new Label(String.valueOf(index)));
//                vPath.getChildren().set(index, realHomePath);
//            }
//    }
//
//    public void horizontalPathLabeling(HBox hPath) {
//        for (int index = 1; index < 6; ++index) {
//            StackPane realHomePath = new StackPane();
//            realHomePath.getChildren().addAll(hPath.getChildren().get(index), new Label(String.valueOf(index)));
//            hPath.getChildren().set(index, realHomePath);
//        }
//    }
    // exports scene

}


// des refs: https://stackoverflow.com/questions/12201712/how-to-find-an-element-with-an-id-in-javafx