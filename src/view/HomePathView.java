package view;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class HomePathView {

    private HBox horzPathTile;
    private VBox vertPathTile;
    private Color color;
    private boolean isHorizontal;

    public HomePathView(HBox hPath, Color color) {
        this.horzPathTile = hPath;
        this.color = color;
        isHorizontal = true;
        getPathTile(0);

    };

    public HomePathView(VBox vPath, Color color) {
        this.vertPathTile = vPath;
        this.color = color;
        isHorizontal = false;
        getPathTile(1);
    }

    public ObservableList<Node> getPathContents(int type) {
        if (type == 0) return horzPathTile.getChildren();   // 0 -> horizontal tile
        else return vertPathTile.getChildren();        // 1 -> vertical tile
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

    private StackPane makeTile(int index) {
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
        tileNumber.setTextFill(Color.WHITE);
        tileNumber.setStyle("-fx-font: 24 arial; -fx-font-weight: 800");
        return new StackPane(tile, tileNumber);
    }

    public void getPathTile(int type) {
        getPathContents(type).add(startingCircle());
        for (int index = 1; index <= 6; ++index) {
            getPathContents(type).add(makeTile(index));
            taggingHomePaths(type);
        }
    }

    private void taggingHomePaths(int type) {
        int id = 11;
        for (Node node: getPathContents(type)) {
            node.setId(color + "_" + id);
            id++;
        }
    }

    public void onClickedEvent() {
        if (isHorizontal) {
            for (Node path: getPathContents(0)) {
                path.setOnMouseClicked(event -> {
                    path.setOpacity(0.7);
                    System.out.println(path.getId());
                });
            }
        }
        else {
            for (Node path: getPathContents(1)) {
                path.setOnMouseClicked(event -> {
                    path.setOpacity(0.7);
                    System.out.println(path.getId());
                });
            }
        }
    }

}
