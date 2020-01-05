package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Color;
import model.GameModel;
import model.PlayerType;
import view.GameView;

import java.io.FileInputStream;
import java.io.IOException;

public class GameController {

    Stage stage;
    static GameController controller;
    GameView gameView;
    GameModel game = new GameModel();

    private GameController() throws IOException {
        stage = new Stage();
        FXMLLoader loader = new FXMLLoader((getClass().getResource("../view/pachisi.fxml")));
        Parent root = loader.load();
        gameView = new GameView((HBox) root);
        Scene primaryScene = new Scene(root, 820, 820);
        //primaryScene.getStylesheets().add(getClass().getResource("/view/debug.css").toExternalForm());
        stage.setScene(primaryScene);
        controllerDemo(gameView);
    }

    public static GameController getInstance() throws IOException {
        if (controller == null)
            controller = new GameController();
        return controller;
    }

    public void update() {
            stage.show();
    }

    // this concerns with which object to call
    private void controllerDemo(GameView view) {
        view.nestEvent();
        view.pathEvents();
        view.setHomePathEvent();
    }

    // you can see that the nest fades by half




}
