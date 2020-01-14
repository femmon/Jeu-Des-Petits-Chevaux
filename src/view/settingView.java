/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2019C
  Assessment: Final Project
  Created date: 14/01/2020
  By: Phuc Quang, Tran Quang, Duc, Hong, Van
  Last modified: 14/01/2020
  By: Phuc Quang, Tran Quang, Duc, Hong, Van
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/
package view;

import controller.GameController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class settingView {
    GameController gameController;
    @FXML
    private Button EngButton, VnButton, StrtButton;
    @FXML
    private VBox playerCheckBox, comCheckBox;
    private Parent playerSettingView;

    @FXML
    public void initialize() throws IOException{
        playerSettingView = FXMLLoader.load(getClass().getResource("PlayerSettingView.fxml"));
        playerCheckBox = (VBox) playerSettingView.lookup("#playerCheckBox");
        comCheckBox = (VBox) playerSettingView.lookup("#comCheckBox");
        StrtButton = (Button) playerSettingView.lookup("#StrtButton");

        StrtButton.setOnMouseClicked(e -> {
            int playerCounter = 0;

            for (int i = 1; i < 5; i++) {
                if (((CheckBox) playerCheckBox.getChildren().get(i)).isSelected() ||
                        ((CheckBox) comCheckBox.getChildren().get(i)).isSelected()) {
                    playerCounter++;

                }
            }
            if (playerCounter < 2){
                Stage stage = new Stage();
                Text text = new Text(Language.getInstance().getString("playerCount"));
                GridPane gridPane = new GridPane();
                gridPane.add(text,0, 0);
                gridPane.setAlignment(Pos.CENTER);
                stage.setScene(new Scene(gridPane,200, 200));
                stage.show();

                return;
            }

            // Get current stage
            Stage stage = (Stage) StrtButton.getScene().getWindow();

            getPlayerInfo();
            gameController.playGame();

        });

        for (int i = 1; i < 5; i++) {
            int finalI = i;
            playerCheckBox.getChildren().get(i).setOnMouseClicked(e -> {
                CheckBox right = (CheckBox) comCheckBox.getChildren().get(finalI);
                right.setDisable(!right.isDisabled());
            });

            comCheckBox.getChildren().get(i).setOnMouseClicked(e -> {
                CheckBox right = (CheckBox) playerCheckBox.getChildren().get(finalI);
                right.setDisable(!right.isDisabled());
            });
        }
    }

    private void getPlayerInfo() {
        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<Boolean> human = new ArrayList<>();
        ArrayList<Boolean> com = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            VBox playerList = (VBox) playerSettingView.lookup("#playerList");
            String name = ((TextField) playerList.getChildren().get(i)).getText();
            nameList.add(name);

            human.add(((CheckBox) playerCheckBox.getChildren().get(i)).isSelected());
            com.add(((CheckBox) comCheckBox.getChildren().get(i)).isSelected());
        }

        gameController.setPlayerList(nameList, human, com);
    }

    public void toPlayerSetting(ActionEvent e) {
        Stage stage;

        if (e.getSource() == EngButton) {
            stage = (Stage) EngButton.getScene().getWindow();
            Language.getInstance().setLanguage("en");

        } else {
            stage = (Stage) VnButton.getScene().getWindow();
            Language.getInstance().setLanguage("vi");
        }

        Scene scene = new Scene(playerSettingView);
        updateLanguagePlayerSetting();
        stage.setScene(scene);
        stage.setTitle("Settings");
        stage.show();
    }

    private void updateLanguagePlayerSetting() {
        Language language = Language.getInstance();
        ((Label) playerSettingView.lookup("#teamColor")).setText(language.getString("teamColor"));
        ((Label) playerSettingView.lookup("#name")).setText(language.getString("name"));
        ((Label) playerSettingView.lookup("#player")).setText(language.getString("player"));
        ((Label) playerSettingView.lookup("#com")).setText(language.getString("com"));
        ((Label) playerSettingView.lookup("#rED")).setText(language.getString("rED"));
        ((Label) playerSettingView.lookup("#bLUE")).setText(language.getString("bLUE"));
        ((Label) playerSettingView.lookup("#yELLOW")).setText(language.getString("yELLOW"));
        ((Label) playerSettingView.lookup("#gREEN")).setText(language.getString("gREEN"));
        ((Button) playerSettingView.lookup("#StrtButton")).setText(language.getString("StrtButton"));

    }

    public void initData(GameController gameController) {
        this.gameController = gameController;
    }
}
