package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class GameView {


    @FXML StackPane blueNest;
    @FXML StackPane yellowNest;
    @FXML StackPane redNest;
    @FXML StackPane greenNest;


    public GameView(GridPane pachisi) {
        blueNest = (StackPane) pachisi.lookup("#blueNest");
        yellowNest = (StackPane) pachisi.lookup("#yellowNest");
        redNest = (StackPane) pachisi.lookup("#redNest");
        greenNest = (StackPane) pachisi.lookup("#greenNest");
        NestView selectedNest = new NestView(blueNest);
        selectedNest.addHorse();
        selectedNest.getHorseCount();
    }

    // obtains scene from the fxml
//    public void getScene(Scene scene) throws IOException {
//
//    }

    public StackPane getBlueNest() {
        return blueNest;
    }



    // exports scene

}


// des refs: https://stackoverflow.com/questions/12201712/how-to-find-an-element-with-an-id-in-javafx