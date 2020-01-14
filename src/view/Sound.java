package view;

import javafx.scene.media.*;
import javafx.util.Duration;

class Sound {
    private MediaPlayer mediaPlayer;

    Sound(String fileName) {
        Media media = new Media(getClass().getResource("/src/Music/" + fileName).toString());
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

