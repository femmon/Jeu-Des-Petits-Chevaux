package view;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class HomePathView {

    private HBox horzPathTile;
    private VBox vertPathTile;
    private Color color;

    public HomePathView(HBox hPath, Color color) {
        this.horzPathTile = hPath;
        this.color = color;
    };

    public HomePathView(VBox vPath, Color color) {
        this.vertPathTile = vPath;
        this.color = color;
    }

    private StackPane startingCircle() {
        StackPane startingStep = new StackPane();
        Circle startPath = new Circle(18);
        startPath.setStroke(color);
        startPath.setFill(Color.rgb(255,255,255));
        startPath.setStrokeWidth(2);
        startingStep.getChildren().add(startPath);
        startingStep.setAlignment(Pos.CENTER);
        return startingStep;
    }

    private StackPane makeTile(int index, boolean isHorizontal) {
        Rectangle tile = new Rectangle();
        tile.setArcHeight(5);
        tile.setArcWidth(5);
        tile.setFill(color);
        tile.setStroke(color);
        if (isHorizontal) return makeTallTile(tile, index);
        else return makeWideTile(tile, index);
    }

    private StackPane makeWideTile(Rectangle tile, int index) {
        tile.setHeight(40);
        tile.setWidth(158);
        Label tileNumber = new Label(String.valueOf(index));
        tileNumber.setTextFill(Color.WHITE);
        tileNumber.setStyle("-fx-font: 24 arial; -fx-font-weight: 800");
        return new StackPane(tile, tileNumber);
    }

    private StackPane makeTallTile(Rectangle tile, int index) {
        tile.setHeight(158);
        tile.setWidth(40);
        Label tileNumber = new Label(String.valueOf(index));
        tileNumber.setTextFill(Color.BLACK);
        tileNumber.setStyle("-fx-font: 24 arial; -fx-font-weight: 800");
        return new StackPane(tile, tileNumber);
    }


    public void getHorzPathTile() {
        horzPathTile.getChildren().add(startingCircle());
        for (int index = 1; index <= 6; ++index) {
            horzPathTile.getChildren().add(makeTile(index, true));
        }
    }

    public void getVertPathTile() {
        vertPathTile.getChildren().add(startingCircle());
        for (int index = 1; index <= 6; ++index) {
                vertPathTile.getChildren().add(makeTile(index, false));
        }
    }
    
    public ObservableList<Node> getPathContents(int type) {
        if (type == 0) return horzPathTile.getChildren();
        else return vertPathTile.getChildren();
    }

}
