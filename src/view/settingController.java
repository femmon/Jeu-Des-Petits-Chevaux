package view;

import controller.GameController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class settingController {
    GameController gameController;
    @FXML
    private Button EngButton, VnButton, StrtButton;
    @FXML
    private VBox playerCheckBox, comCheckBox;
    private Parent playerSettingView;
    private HBox board;

    @FXML
    public void initialize() throws IOException{
        playerSettingView = FXMLLoader.load(getClass().getResource("PlayerSettingView.fxml"));
        playerCheckBox = (VBox) playerSettingView.lookup("#playerCheckBox");
        comCheckBox = (VBox) playerSettingView.lookup("#comCheckBox");
        StrtButton = (Button) playerSettingView.lookup("#StrtButton");

        StrtButton.setOnMouseClicked(e -> {
            // Get current stage
            Stage stage = (Stage) StrtButton.getScene().getWindow();

            Scene primaryScene = new Scene(board, 820, 820);
            stage.setScene(primaryScene);
            stage.setTitle("Pachisi");
            stage.show();

            getPlayerInfo();
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

    }

    public void initData(GameController gameController, HBox board) {
        this.gameController = gameController;
        this.board = board;
    }
}
