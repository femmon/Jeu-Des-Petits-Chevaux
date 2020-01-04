package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;
import view.*;

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
        gameView = new GameView((GridPane) root);
        Scene primaryScene = new Scene(root, 820, 820);
        //primaryScene.getStylesheets().add(getClass().getResource("/view/debug.css").toExternalForm());
        stage.setScene(primaryScene);
//        controllerDemo();
//        displayDice();
        gameModelTest();
    }

    public static GameController getInstance() throws IOException {
        if (controller == null)
            controller = new GameController();
        return controller;
    }

    public void update() {
            stage.show();
    }

    private void controllerDemo() {
        fadeBlueNest();
    }

    // you can see that the nest fades by half
    private void fadeBlueNest() {
        gameView.getBlueNest().get(0).setOpacity(0.5);
    }

    //gameModel test
    private void gameModelTest() {
        game.playGame();
    }

    //Test Dice
    public void displayDice() {
        RollDices rollDices = new RollDices(4);
        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(rollDices);

        Scene secondScene = new Scene(secondaryLayout, 300, 300);
        Stage newWindow = new Stage();
        newWindow.setTitle("Dice");
        newWindow.setScene(secondScene);
        newWindow.show();
    }


}
