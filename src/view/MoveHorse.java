/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2019C
  Assessment: Final Project
  Created date: 14/01/2020
  By: Phuc Quang, Tran Quang, Duc, Hong, Van
  Last modified: 14/01/2020
  By: Phuc Quang, Tran Quang, Duc, Hong, Van
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/
package view;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class MoveHorse extends StackPane {
    private ImageView horse;
    //private int clickCount = 0;

    public MoveHorse(ImageView horse) {
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
