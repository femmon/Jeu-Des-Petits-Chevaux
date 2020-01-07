package view;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;


/**
 * PathView draws path and handles command from GameView.
 * PathView is the only View subclass which has to drawn outside of its scope ( i.e in GameView )
 * Parent: GameView
 */
public class PathView {
    HBox path;
    Color color;

    public PathView(HBox path, Color color) {
        this.path = path;
        this.color = color;
    }

    private StackPane createCircle() {
        Circle step = new Circle(18);
        step.setStroke(color);
        step.setFill(Color.WHITE);
        StackPane circularPath = new StackPane();
        circularPath.getChildren().add(step);
        return circularPath;
    }

    public void fillPath() {
        for (int index = 0; index < 5; index++) {
            path.getChildren().add(createCircle());
        }
    }

    public void fillSecPath() {
        for (int index = 0; index < 6; index++) {
            path.getChildren().add(createCircle());
        }
    }

    /**
     * This concerns with which part of the chosen object you want to modify
     * It is at one abstraction level lower than GameController
     * Controller --( Tells which part to edit )-->> GameView --( Invokes the desired function in the class )-->> PathView --( add/delete sth )
     */
    public void highlightCircle() {
        for (Node node: path.getChildren()) {
            node.setOnMouseClicked(e -> node.setStyle("-fx-border-color: gray; -fx-opacity: 0.2"));
        }
    }

    public void horseOutOfCage(ImageView horseImage) {
        horseImage.setRotate(-90);
        path.getChildren().set(0, horseImage);
    }

}
