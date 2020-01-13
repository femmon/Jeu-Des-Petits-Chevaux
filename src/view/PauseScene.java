package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

class PauseScene extends StackPane {
    private VBox subPane = new VBox();
    private String[] nameBt = {"New Game", "Continue Game", "Play next game", "Quit"};
    private Button[] buttons = new Button[4];

    Button[] getButtons() {
        return buttons;
    }

    PauseScene() {
        BackgroundFill backgroundFill = new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY);

        Label label = new Label();
        label.setPrefSize(820, 820);
        label.setStyle("-fx-background-color: rgba(28, 22, 22, 0.8);");

        //continueGameBt.setPrefSize(150, 40);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button(nameBt[i]);
            buttons[i].setPrefSize(200, 40);
            buttons[i].setBackground(new Background(backgroundFill));
            buttons[i].setTextFill(Color.WHITE);
            subPane.getChildren().add(buttons[i]);
        }

        subPane.setSpacing(10);
        subPane.setAlignment(Pos.CENTER);
        getChildren().addAll(label, subPane);
        setAlignment(Pos.CENTER);
    }
}
