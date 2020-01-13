package model;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

class Music extends HBox {
    private MediaPlayer mediaPlayer;
    private Button[] buttons = new Button[4]; // btPause, btPlay, btVolumeUp, btVolumeDown respectively
    private double volume = 0.5;

    Music(String fileName) {
        String[] nameBt = {"btPause", "btPlay", "btVolumeUp", "btVolumeDown"};
        Media media = new Media(getClass().getResource("/santa_claus/Music/" + fileName).toString());
        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setVolume(volume);
        // Set music to play automatically when user starts the game
        mediaPlayer.setAutoPlay(true);
        // Set music to replay automatically when the audio ends
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.play();
        });
        // Insert images from computer
        ImageView[] images = new ImageView[4];
        for (int i = 0; i < images.length; i++) {
            images[i] = new ImageView("file:src/santa_claus/Images/" + nameBt[i] + ".png");
            setImages(images[i]);
        }
        // Get 4 buttons
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button("", images[i]);
        }

        setPauseMusic();
        setPlayMusic();
        // Add btPause to pane Music
        getChildren().addAll(buttons[0], buttons[2], buttons[3]);
        setSpacing(5);
    }

    void stopMusic() {
        mediaPlayer.stop();
    }

    private void setPauseMusic() { // Action after user presses btPause
        buttons[0].setOnAction(ActionEvent -> {
            getChildren().remove(buttons[0]);
            getChildren().add(0, buttons[1]);
            mediaPlayer.pause();
        });
    }

    private void setPlayMusic() { // Action when user presses btPlay
        buttons[1].setOnAction(ActionEvent -> {
            getChildren().remove(buttons[1]);
            getChildren().add(0, buttons[0]);
            mediaPlayer.play();
        });
    }

    private void setImages(ImageView image) {
        image.setFitWidth(35);
        image.setPreserveRatio(true);
        image.setSmooth(true);
    }
}

