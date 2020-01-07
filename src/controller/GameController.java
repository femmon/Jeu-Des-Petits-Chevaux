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
     * 0 : Red nest
     * 1 : Green nest
     * 2 : Blue nest
     * 3 : Yellow nest
     * @param view
     */
    private void controllerDemo(GameView view) {
        view.nestEvent(1);
        view.nestEvent(2);

        
        view.homePathEvent(1);
    }

    // you can see that the nest fades by half

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

//-------------------------GAME PLAY-------------------------------

    //for set turn order
    private void throwDice(int diceValue) {

    }

    //for throw dice for move
    private void throwDice(int diceValue1, int diceValue2) {

    }

    private void pickDicevalue() {

    }

    private void displayDiceValue() {

    }

    private void pickHorse() {

    }

    private void summonHorse() {

    }

    private void moveHorse() {

    }


}
