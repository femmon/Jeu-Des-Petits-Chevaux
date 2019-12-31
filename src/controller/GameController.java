package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Color;
import model.GameModel;
import model.PlayerType;
import view.GameView;

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
        controllerDemo();
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
        game.setPlayer("1", PlayerType.HUMAN, Color.BLUE);
        game.setPlayer("2", PlayerType.MACHINE, Color.RED);
        game.setPlayer("3", PlayerType.HUMAN, Color.GREEN);
        game.setPlayer("4", PlayerType.MACHINE, Color.YELLOW);
        game.setTurnOrder();
        System.out.println(game.getPlayerList().get(0).getName());
        System.out.println(game.getPlayerList().get(1).getName());
        System.out.println(game.getPlayerList().get(2).getName());
        System.out.println(game.getPlayerList().get(3).getName());
    }


}
