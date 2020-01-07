package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class settingController {

    @FXML
    private Button EngButton, VnButton, StrtButton;

    @FXML
    private VBox playerCheckBox, comCheckBox;

    private Parent finalSetting;

    @FXML
    public void initialize() throws IOException{
        finalSetting = FXMLLoader.load(getClass().getResource("FinalSetting.fxml"));
        playerCheckBox = (VBox) finalSetting.lookup("#playerCheckBox");
        comCheckBox = (VBox) finalSetting.lookup("#comCheckBox");
        StrtButton = (Button) finalSetting.lookup("#StrtButton");

        StrtButton.setOnMouseClicked(e -> {
            try {
                Stage stage1;
                Parent root;

                if (e.getSource() == StrtButton);
                stage1 = (Stage) StrtButton.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("pachisi.fxml"));

                Scene scene = new Scene(root);
                stage1.setScene(scene);
                stage1.setTitle("Pachisi");
                stage1.show();

                getPlayerInfo();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
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
        for (int i = 1; i < 5; i++) {
            VBox playerList = (VBox) finalSetting.lookup("#playerList");
            String name = ((TextField) playerList.getChildren().get(i)).getText();
            System.out.println(name);

            System.out.println("Human: " + ((CheckBox) playerCheckBox.getChildren().get(i)).isSelected());
            System.out.println("COM: " + ((CheckBox) comCheckBox.getChildren().get(i)).isSelected());
        }
    }

    public void toPlayerSetting(ActionEvent e) {
        Stage stage;

        if (e.getSource() == EngButton) {
            stage = (Stage) EngButton.getScene().getWindow();

        } else {
            stage = (Stage) VnButton.getScene().getWindow();
        }

        Scene scene = new Scene(finalSetting);
        stage.setScene(scene);
        stage.setTitle("Settings");
        stage.show();
    }
}








