package view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * This function fetches UI templates from FXML board + sets up the board + updates the board accordingly in correspondence with GameController
 * */

public class GameView {

    @FXML HBox pachisi;
    // 4 nests
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


    NestView[] nestInstances = new NestView[4];
    HomePathView[] homePathInstances = new HomePathView[4];
    PathView[] allPaths = new PathView[8];

    // 1. obtains empty components from the fxml
    public GameView(HBox pachisi) throws FileNotFoundException {
        this.pachisi = pachisi;
        fetchingNests(pachisi);
        fetchingHomePath(pachisi);
        fetchingPaths(pachisi);

    }

    public void fetchingNests(HBox pachisi) throws FileNotFoundException {
        blueNest = (StackPane) pachisi.lookup("#blueNest");
        yellowNest = (StackPane) pachisi.lookup("#yellowNest");
        redNest = (StackPane) pachisi.lookup("#redNest");
        greenNest = (StackPane) pachisi.lookup("#greenNest");
        StackPane[] nests = {redNest, greenNest, blueNest, yellowNest};
        nestInit(nests);
    }

    private void fetchingHomePath(HBox pachisi) {
        home_path_blue = (VBox) pachisi.lookup("#home_path_blue");
        home_path_green = (VBox) pachisi.lookup("#home_path_green");
        home_path_yellow = (HBox) pachisi.lookup("#home_path_yellow");
        home_path_red = (HBox) pachisi.lookup("#home_path_red");

        VBox[] vertPaths = {home_path_blue, home_path_green};
        HBox[] horzPaths = {home_path_yellow, home_path_red};
        initHomePaths(vertPaths, horzPaths);
    }

    private void fetchingPaths(HBox pachisi) {
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
        initPaths(paths, secPaths, allPaths);
    }


    // 1.2 drawing StackPanes on each HBox | VBox element
    private void initHomePaths(VBox[] vertHomePaths, HBox[] horzHomePaths) {
        homePathInstances[0] = new HomePathView(vertHomePaths[0], Color.BLUE);  // blue home path
        homePathInstances[1] = new HomePathView(vertHomePaths[1], Color.GREEN); // green home path
        homePathInstances[2] = new HomePathView(horzHomePaths[0], Color.ORANGE); // yellow home path
        homePathInstances[3] = new HomePathView(horzHomePaths[1], Color.RED); // red home path
    }

    // event handler of Home Path
    public void setHomePathEvent() {
        homePathInstances[2].onClickedEvent();
    }


    private void initPaths(HBox[] paths, HBox[] secPaths, PathView[] allPaths) {
        Color[] COLOR_LIST =  {Color.BLUE, Color.RED, Color.ORANGE, Color.GREEN};
        int index = 0, index2 = 0;
        for (HBox path: paths) {
            PathView pathDrawer = new PathView(path, COLOR_LIST[index]);
            pathDrawer.fillPath();
            allPaths[index] = pathDrawer;
            index++;
        }

        for (HBox secPath: secPaths) {
            PathView pathDrawer = new PathView(secPath, COLOR_LIST[index2]);
            pathDrawer.fillSecPath();
            allPaths[index2 + 4] = pathDrawer;
            index2++;
        }
    }

    public void pathEvents() {
        for (int index = 0; index <= 7; index++) {
            allPaths[index].highlightCircle();
        }
    }

    // 2. updates components as per request from Controller (e.g adding horse to path, home path or nest )

    // adding horses to the nest
    private void nestInit(StackPane[] nests) throws FileNotFoundException {
        int order = 0;

        for (StackPane nest: nests) {
            nestInstances[order] = new NestView(nest, order);
            order++;
        }
    }

    public void nestEvent() {
        nestInstances[0].removeHorse();
        nestInstances[1].onHorseSelectedEvent();
    }

    // 3. Outsourcing components for controller to handle
    public ObservableList<Node> getBlueNest() {
        return blueNest.getChildren();
    }



}


// des refs: https://stackoverflow.com/questions/12201712/how-to-find-an-element-with-an-id-in-javafx
