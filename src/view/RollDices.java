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
    private int seconds;
    private ImageView imageDice = new ImageView();
    private Timeline timer;
    private Dice dice;

    public RollDices(Dice dice) {
        this.dice = dice;

        Dice randomDice = new Dice();
        randomDice.throwDice();
        imageDice.setImage(new Image("file:src/view/diceImage/" + randomDice.getDiceValue() + ".png",
                100, 100, true, true));
        getChildren().add(imageDice);
        setAlignment(Pos.CENTER);

        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> setTimeline()));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void setTimeline() {
        seconds++;
        if (seconds == 1) {
            setAnimation(dice.getDiceValue());
        }
        else if (seconds == 3) {
            timer.stop();
        }
    }

    private void setAnimation (int diceValue) {
        RotateTransition rt = new RotateTransition(Duration.seconds(1), imageDice);
        rt.setFromAngle(0);
        rt.setToAngle(360);
        rt.setOnFinished(event -> imageDice.setImage(new Image("file:src/view/diceImage/" + dice.getDiceValue() + ".png",
                100, 100, true, true)));
        rt.play();
    }
}
