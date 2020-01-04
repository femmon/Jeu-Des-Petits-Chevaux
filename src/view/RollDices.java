package view;

import javafx.animation.RotateTransition;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import model.Dice;

class RollDices extends GridPane {
    int dice = 0;
    private ImageView imageDice = new ImageView();

    RollDices(int dice) {
        this.dice = dice;
        setOnMouseClicked(event -> setAnimation());
        getNewValue();
        addRow(0, imageDice);
        setAlignment(Pos.CENTER);
    }

    private void setAnimation () {
        RotateTransition rt = new RotateTransition(Duration.seconds(1), imageDice);
        rt.setFromAngle(0);
        rt.setToAngle(360);
        rt.play();

//        RotateTransition newRt = new RotateTransition(Duration.seconds(1), imageDice2);
//        newRt.setFromAngle(0);
//        newRt.setToAngle(360);
//        newRt.setOnFinished(event -> getNewValue());
//        newRt.play();
    }

    private void getNewValue() {
        imageDice.setImage(new Image("file:src/santa_claus/Images/" + dice + ".png",
                100, 100, true, true));
    }
}
