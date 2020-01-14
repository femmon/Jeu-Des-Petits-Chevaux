package view;

import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.Dice;

public class RollDices extends GridPane {
    private ImageView diceImage = new ImageView();
    private int i = 1;
    private Timeline timer;
    private double countTime = 0;
    private Dice dice;
    private Sound diceSound = new Sound();

    public RollDices(Dice dice) {
        this.dice = dice;

        diceImage.setImage(new Image("file:src/view/dice/" + this.dice.getDiceValue() + ".png", 100, 100, true, true));
        setAnimation(diceImage);
        getChildren().add(diceImage);
        setAlignment(Pos.CENTER);
    }

    private void setNewAnimation() {
        diceSound.startSound();
        diceImage.setImage(new Image("file:src/view/dice-random/" + i + ".png", 100, 100, true, true));
        i++;
        if (i > 6) {
            i = 1;

        }
    }

    private void setAnimation(ImageView diceImage) {
        timer = new Timeline(new KeyFrame(Duration.seconds(0.15), e -> {
            countTime = countTime + 0.15;

            setNewAnimation();
            if (countTime > 1) {
                diceSound.stopSound();
                timer.stop();
                diceImage.setImage(new Image("file:src/view/dice/" + dice.getDiceValue() + ".png", 100, 100, true, true));
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }
}
