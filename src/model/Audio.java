package model;

import javafx.scene.media.Media;
import javafx.scene.Group;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;

public class Audio {
    private String path = "";
    private Media media = new Media(new File(path).toURI().toString());
    private MediaPlayer mediaPlayer = new MediaPlayer(media);

    public Audio(String path) {
         this.path = path;
    }

    public void start() {
        mediaPlayer.setAutoPlay(true);
    }

    public void stop() {
        mediaPlayer.setAutoPlay(false);
}
}
