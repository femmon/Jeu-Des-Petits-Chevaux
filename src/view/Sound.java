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

