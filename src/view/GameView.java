package view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Stack;


public class GameView {

    // 4 nests
    @FXML GridPane pachisi;

    @FXML StackPane blueNest;
    @FXML StackPane yellowNest;
    @FXML StackPane redNest;
    @FXML StackPane greenNest;

    // 4 home paths
    @FXML VBox home_path_blue;
    @FXML VBox home_path_green;
    @FXML HBox home_path_yellow;
    @FXML HBox home_path_red;

    // 8 paths
    @FXML
    HBox bluePath1; HBox bluePath2;
    HBox redPath1; HBox redPath2;
    HBox yellowPath1; HBox yellowPath2;
    HBox greenPath1; HBox greenPath2;

    // 1. obtains empty components from the fxml
    public GameView(GridPane pachisi) {
        this.pachisi = pachisi;
        fetchingNests(pachisi);
        fetchingHomePath(pachisi);
        fetchingPaths(pachisi);
    }

    public void fetchingNests(GridPane pachisi) {
        blueNest = (StackPane) pachisi.lookup("#blueNest");
        yellowNest = (StackPane) pachisi.lookup("#yellowNest");
        redNest = (StackPane) pachisi.lookup("#redNest");
        greenNest = (StackPane) pachisi.lookup("#greenNest");
        getContentsOf(blueNest);
    }

    private void fetchingHomePath(GridPane pachisi) {
        home_path_blue = (VBox) pachisi.lookup("#home_path_blue");
        home_path_green = (VBox) pachisi.lookup("#home_path_green");
        home_path_yellow = (HBox) pachisi.lookup("#home_path_yellow");
        home_path_red = (HBox) pachisi.lookup("#home_path_red");
        drawHomePaths(home_path_blue, home_path_green, home_path_yellow, home_path_red);
    }

    private void fetchingPaths(GridPane pachisi) {
        bluePath1 = (HBox) pachisi.lookup("#bluePath1");
        redPath1 = (HBox) pachisi.lookup("#redPath1");
        yellowPath1 = (HBox) pachisi.lookup("#yellowPath1");
        greenPath1 = (HBox) pachisi.lookup("#greenPath1");
        bluePath2 = (HBox) pachisi.lookup("#bluePath2");
        redPath2 = (HBox) pachisi.lookup("#redPath2");
        yellowPath2 = (HBox) pachisi.lookup("#yellowPath2");
        greenPath2 = (HBox) pachisi.lookup("#greenPath2");
        HBox[] paths = {bluePath1, redPath1, yellowPath1, greenPath1};
        HBox[] secPaths = {bluePath2, redPath2, yellowPath2, greenPath2};
        drawPaths(paths, secPaths);
    }

    // 1.1 demo to show that you can access nest component
    public void getContentsOf(StackPane nest) {
        NestView selectedNest = new NestView(nest);
        System.out.println(nest.getId() + " : " + selectedNest.getNestContents());
        nest.getChildren().get(0).setOpacity(1);
    }

    // 1.2 drawing StackPanes on each HBox | VBox element
    private void drawHomePaths(VBox bluePath, VBox greenPath, HBox yellowPath, HBox redPath) {
        HomePathView bluePathView = new HomePathView(bluePath, Color.BLUE);
        HomePathView greenPathView = new HomePathView(greenPath, Color.GREEN);
        HomePathView yellowPathView = new HomePathView(yellowPath, Color.YELLOW);
        HomePathView redPathView = new HomePathView(redPath, Color.RED);
        bluePathView.getVertPathTile();
        greenPathView.getVertPathTile();
        yellowPathView.getHorzPathTile();
        redPathView.getHorzPathTile();
        homePathDemo(bluePathView);
        vertHomePathDemo(yellowPathView);
    }



    private void drawPaths(HBox[] paths, HBox[] secPaths) {
        Color[] COLOR_LIST =  {Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN};
        int index = 0, index2 = 0;
        for (HBox path: paths) {
            PathView pathDrawer = new PathView(path, COLOR_LIST[index]);
            pathDrawer.fillPath();
            index++;
        }

        for (HBox secPath: secPaths) {
            PathView pathDrawer = new PathView(secPath, COLOR_LIST[index2]);
            pathDrawer.fillSecPath();
            index2++;
        }

    }
    // 2. updates components as per request from Controller (e.g adding horse to path, home path or nest )

        // 2.1 demo examples - can also be reviewed by adding on click events
        private void homePathDemo(HomePathView homePathView) {
            System.out.println(homePathView.getPathContents(1).get(2));
        }

        private void vertHomePathDemo(HomePathView homePathView) {
            System.out.println(homePathView.getPathContents(0).get(2));
        }

    // 3. Outsourcing components for controller to handle

    public ObservableList<Node> getBlueNest() {
        return blueNest.getChildren();
    }

}


// des refs: https://stackoverflow.com/questions/12201712/how-to-find-an-element-with-an-id-in-javafx