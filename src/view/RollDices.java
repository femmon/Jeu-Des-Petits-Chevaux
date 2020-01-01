package view;

import javafx.animation.RotateTransition;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.Random;

class RollDices extends GridPane {
    private ImageView dice1 = new ImageView();
    private ImageView dice2 = new ImageView();
    private byte valueDice1, valueDice2;

    byte getValueDice1() {
        return valueDice1;
    }
    byte getValueDice2() {
        return valueDice2;
    }

    RollDices() {
        setOnMouseClicked(event -> setAnimation());
        getRandomNumbers();
        addRow(0, dice1, dice2);
        setAlignment(Pos.CENTER);
    }

    private void setAnimation () {
        RotateTransition rt = new RotateTransition(Duration.seconds(1), dice1);
        rt.setFromAngle(0);
        rt.setToAngle(360);
        rt.play();

        RotateTransition newRt = new RotateTransition(Duration.seconds(1), dice2);
        newRt.setFromAngle(0);
        newRt.setToAngle(360);
        newRt.setOnFinished(event -> getRandomNumbers());
        newRt.play();
    }

    private void getRandomNumbers() {
        valueDice1 = (byte) (new Random().nextInt(6) + 1);
        valueDice2 = (byte) (new Random().nextInt(6) + 1);
        dice1.setImage(new Image("file:src/santa_claus/Images/" + valueDice1 + ".png",
                100, 100, true, true));
        dice2.setImage(new Image("file:src/santa_claus/Images/" + valueDice2 + ".png",
                100, 100, true, true));
    }
}