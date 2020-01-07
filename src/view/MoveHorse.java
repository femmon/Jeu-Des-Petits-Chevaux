package view;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

class MoveHorse extends StackPane {
    private ImageView horse;
    //private int clickCount = 0;

    MoveHorse(ImageView horse) {
        this.horse = horse;

        /*setOnMouseClicked(event -> {
            if (clickCount % 2 == 0) {
                fadeOut();
            } else {
                fadeIn();
            }
        });*/

        getChildren().add(horse);
        setAlignment(Pos.CENTER);
    }

    void fadeIn() {
        FadeTransition fade = new FadeTransition(Duration.seconds(0.7), horse);
        fade.setToValue(1);
        fade.play();
        //clickCount++;
    }

    void fadeOut() {
        FadeTransition fade = new FadeTransition(Duration.seconds(0.7), horse);
        fade.setToValue(0);
        fade.play();
        //clickCount++;
    }
}
