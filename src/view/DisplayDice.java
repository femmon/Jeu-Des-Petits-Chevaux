package view;

import controller.GameController;
import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;

import java.io.IOException;

public class DisplayDice {
    private int seconds;
    private Stage diceWindow;
    private Timeline timer;
    private GameController gameController;

    public DisplayDice() {
        try {
            gameController = GameController.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void displayDice(Dice dice) {
        diceWindow = new Stage();
        RollDices rollDice = new RollDices(dice);

        timer = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            diceWindow.close();
            timer.stop();
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();

        diceWindow.setScene(new Scene(rollDice, 420, 420));
        diceWindow.setTitle("Roll a dice");
        diceWindow.show();
    }

    public void displayDiceWithoutBtn(Dice dice1, Dice dice2) {
        diceWindow = new Stage();
        RollDices rollDices1 = new RollDices(dice1);
        RollDices rollDices2 = new RollDices(dice2);
        HBox hBox = new HBox();

        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(rollDices1, rollDices2);

        timer = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            diceWindow.close();
            timer.stop();
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();

        diceWindow.setScene(new Scene(hBox, 420, 420));
        diceWindow.setTitle("Roll a dice");
        diceWindow.show();
    }

    public void displayDice(Dice dice1, Dice dice2) {
        diceWindow = new Stage();
        seconds = 0;
        VBox pane = new VBox();
        HBox[] boxes = new HBox[2]; // imageBox, btBox respectively
        RollDices rollDices1 = new RollDices(dice1);
        RollDices rollDices2 = new RollDices(dice2);
        Button[] buttons = new Button[3]; // Dice 1, Dice 2, Both dices respectively
        String[] btName = {"Pick Dice 1", "Pick Dice 2", "Pick Both"};

        for (int i = 0; i < boxes.length; i++) { // Set imageBox and btBox
            boxes[i] = new HBox();
            boxes[i].setSpacing(10);
            boxes[i].setAlignment(Pos.CENTER);
        }

        boxes[0].getChildren().addAll(rollDices1, rollDices2);
        for (int i = 0; i < buttons.length; i++) {// set each bts and add them to btBox
            buttons[i] = new Button(btName[i]);
            boxes[1].getChildren().add(i, buttons[i]);
        }

        pane.getChildren().addAll(boxes[0], boxes[1]);// add 2 boxes to another vbox
        pane.setSpacing(10);
        pane.setAlignment(Pos.CENTER);

        buttons[0].setOnMouseClicked(event -> {
            if (seconds == 2) {
                buttons[0].setCancelButton(true);
                diceWindow.close();
                gameController.setClickedDice(0);
            }
        }); // Set btDice1

        buttons[1].setOnMouseClicked(event -> {
            if (seconds == 2) {
                buttons[1].setCancelButton(true);
                diceWindow.close();
                gameController.setClickedDice(1);
            }
        }); // Set btDice2 when clicked

        buttons[2].setOnMouseClicked(event -> {
            if (seconds == 2) {
                buttons[2].setCancelButton(true);
                diceWindow.close();
                gameController.setClickedDice(2);
            }
        }); // Set btDice3 when clicked

        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            seconds++;
            if (seconds == 2) { // When dice rolling animation ended
                timer.stop();
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();

        diceWindow.setScene(new Scene(pane, 400, 200));
        diceWindow.setTitle("Roll 2 dices");
        diceWindow.show();
    }

    public void display1Dice(Dice dice) {
        diceWindow = new Stage();
        seconds = 0;
        VBox pane = new VBox();
        HBox[] boxes = new HBox[2]; // imageBox, btBox respectively
        RollDices rollDices = new RollDices(dice);

        Button[] buttons = new Button[2]; // Dice 1, Dice 2, Both dices respectively
        String[] btName = {"Pick Dice ",  "Forfiet turn"};

        for (int i = 0; i < boxes.length; i++) { // Set imageBox and btBox
            boxes[i] = new HBox();
            boxes[i].setSpacing(20);
            boxes[i].setAlignment(Pos.CENTER);
        }

        boxes[0].getChildren().addAll(rollDices);
        for (int i = 0; i < buttons.length; i++) {// set each bts and add them to btBox
            buttons[i] = new Button(btName[i]);
            boxes[1].getChildren().add(i, buttons[i]);
        }

        pane.getChildren().addAll(boxes[0], boxes[1]);// add 2 boxes to another vbox
        pane.setSpacing(20);
        pane.setAlignment(Pos.CENTER);

        buttons[0].setOnMouseClicked(event -> {
            if (seconds == 2) {
                buttons[0].setCancelButton(true);
                diceWindow.close();
                gameController.setClickedDice(0);
            }
        }); // Set btDice1

        buttons[1].setOnMouseClicked(event -> {
            if (seconds == 2) {
                buttons[1].setCancelButton(true);
                diceWindow.close();
                gameController.setClickedDice(1);
            }
        }); // Set btDice2 when clicked

        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            seconds++;
            if (seconds == 2) { // When dice rolling animation ended
                timer.stop();
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();

        diceWindow.setScene(new Scene(pane, 400, 200));
        diceWindow.setTitle("Roll 1 dices");
        diceWindow.show();
    }


}
