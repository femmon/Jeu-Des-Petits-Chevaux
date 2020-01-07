package controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    @FXML
//    private Button EngButton, VnButton, StrtButton;
//


//    public void handleClickedButton(ActionEvent e) throws IOException {
//        Stage stage;
//        Parent root;
//
//        if (e.getSource() == EngButton) {
//            stage = (Stage) EngButton.getScene().getWindow();
//
//        } else {
//            stage = (Stage) VnButton.getScene().getWindow();
//        }
//        root = FXMLLoader.load(getClass().getResource("../view/FinalSetting.fxml"));
//
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.setTitle("Settings");
//        stage.show();
//    }
//
//    public void handleClickedButton1(ActionEvent event) throws IOException {
//        Stage stage1;
//        Parent root;
//
//        if (event.getSource() == StrtButton);
//            stage1 = (Stage) StrtButton.getScene().getWindow();
//            root = FXMLLoader.load(getClass().getResource("../view/pachisi.fxml"));
//
//            Scene scene = new Scene(root);
//            stage1.setScene(scene);
//            stage1.setTitle("Pachisi");
//            stage1.show();
//    }
    
    public void update() {
            stage.show();
    }

    /**
     * The index labeling :
     * 0 : Red nest/horse/homePath
     * 1 : Green nest/horse/homePath
     * 2 : Blue nest/horse/homePath
     * 3 : Yellow nest/horse/homePath
     * @param view
     */
    private void controllerDemo(GameView view) {
        view.summonHorse(1);
        view.summonHorse(2);
        view.pathEvents();
        view.homePathEvent(1);
    }

    // you can see that the nest fades by half

    //gameModel test
    //Test Dice

}
