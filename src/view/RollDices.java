package santa_claus;

import javafx.animation.RotateTransition;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

class RollDices extends GridPane {
    private Dice dice1;
    private Dice dice2;
    private ImageView imageDice1 = new ImageView();
    private ImageView imageDice2 = new ImageView();

    RollDices(Dice dice1, Dice dice2) {
        this.dice1 = dice1;
        this.dice2 = dice2;

        setOnMouseClicked(event -> setAnimation());
        getNewValue();
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
        newRt.setOnFinished(event -> getNewValue());
        newRt.play();
    }

    private void getNewValue() {
        dice1.throwDice();
        imageDice1.setImage(new Image("file:src/santa_claus/Images/" + dice1.getDiceValue() + ".png",
                100, 100, true, true));

        dice2.throwDice();
        imageDice2.setImage(new Image("file:src/santa_claus/Images/" + dice2.getDiceValue() + ".png",
                100, 100, true, true));
    }
}
