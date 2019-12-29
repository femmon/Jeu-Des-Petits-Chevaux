package view;

import javafx.event.Event;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

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
}
