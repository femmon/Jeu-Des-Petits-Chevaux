package santa_claus;

import javafx.animation.RotateTransition;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

class RollDices extends GridPane {
    private ImageView imageDice1 = new ImageView();
    private ImageView imageDice2 = new ImageView();
    private Dice dice1 = new Dice();
    private Dice dice2 = new Dice();


    byte getValueDice1() {
        return dice1.getDiceValue();
    }
    byte getValueDice2() {
        return dice2.getDiceValue();
    }

    RollDices() {
        setOnMouseClicked(event -> setAnimation());
        getRandomNumbers();
        addRow(0, imageDice1, imageDice2);
        setAlignment(Pos.CENTER);
    }
    private void setAnimation () {
        RotateTransition rt = new RotateTransition(Duration.seconds(1), imageDice1);
        rt.setFromAngle(0);
        rt.setToAngle(360);
        rt.play();

        RotateTransition newRt = new RotateTransition(Duration.seconds(1), imageDice2);
        newRt.setFromAngle(0);
        newRt.setToAngle(360);
        newRt.setOnFinished(event -> getRandomNumbers());
        newRt.play();
    }

    private void getRandomNumbers() {
        dice1.throwDice();
        imageDice1.setImage(new Image("file:src/santa_claus/Images/" + dice1.getDiceValue() + ".png",
                100, 100, true, true));

        dice2.throwDice();
        imageDice2.setImage(new Image("file:src/santa_claus/Images/" + dice2.getDiceValue() + ".png",
                100, 100, true, true));
    }
}
