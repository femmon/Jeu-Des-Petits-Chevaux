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

import javafx.scene.media.*;
import javafx.util.Duration;

import java.io.File;
import java.nio.file.Paths;

class Sound {
    private MediaPlayer mediaPlayer;

    Sound() {
        String path = "src/view/music/RollDiceSound.mp3";
        Media media = new Media(Paths.get(path).toUri().toString());
        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setVolume(1);
        // Set music to replay automatically when the audio ends
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.play();
        });
    }

    void startSound() {
        mediaPlayer.play();
    }

    void stopSound() {
        mediaPlayer.stop();
    }
}

