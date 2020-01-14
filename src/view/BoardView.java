/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2019C
  Assessment: Final Project
  Created date: 14/01/2020
  By: Phuc Quang, Tran Quang, Duc, Hong, Van
  Last modified: 14/01/2020
  By: Phuc Quang, Tran Quang, Duc, Hong, Van
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/
package view;

import controller.GameController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BoardView {
    //----------------------------------BOARD-----------------------------------------
    @FXML
    StackPane mainBoard;
    //----------------------------------NEST-------------------------------------------
    @FXML
    AnchorPane blueNest;
    @FXML
    AnchorPane yellowNest;
    @FXML
    AnchorPane greenNest;
    @FXML
    AnchorPane redNest;

    //----------------------------------Path---------------------------------------------
    @FXML
    StackPane blue_0;
    @FXML
    StackPane blue_1;
    @FXML
    StackPane blue_2;
    @FXML
    StackPane blue_3;
    @FXML
    StackPane blue_4;
    @FXML
    StackPane blue_5;
    @FXML
    StackPane blue_6;
    @FXML
    StackPane blue_7;
    @FXML
    StackPane blue_8;
    @FXML
    StackPane blue_9;
    @FXML
    StackPane blue_10;
    @FXML
    StackPane blue_11;
    @FXML
    StackPane blue_12;
    @FXML
    StackPane blue_13;
    @FXML
    StackPane blue_14;
    @FXML
    StackPane blue_15;
    @FXML
    StackPane blue_16;
    @FXML
    StackPane blue_17;

    @FXML
    StackPane yellow_0;
    @FXML
    StackPane yellow_1;
    @FXML
    StackPane yellow_2;
    @FXML
    StackPane yellow_3;
    @FXML
    StackPane yellow_4;
    @FXML
    StackPane yellow_5;
    @FXML
    StackPane yellow_6;
    @FXML
    StackPane yellow_7;
    @FXML
    StackPane yellow_8;
    @FXML
    StackPane yellow_9;
    @FXML
    StackPane yellow_10;
    @FXML
    StackPane yellow_11;
    @FXML
    StackPane yellow_12;
    @FXML
    StackPane yellow_13;
    @FXML
    StackPane yellow_14;
    @FXML
    StackPane yellow_15;
    @FXML
    StackPane yellow_16;
    @FXML
    StackPane yellow_17;

    @FXML
    StackPane green_0;
    @FXML
    StackPane green_1;
    @FXML
    StackPane green_2;
    @FXML
    StackPane green_3;
    @FXML
    StackPane green_4;
    @FXML
    StackPane green_5;
    @FXML
    StackPane green_6;
    @FXML
    StackPane green_7;
    @FXML
    StackPane green_8;
    @FXML
    StackPane green_9;
    @FXML
    StackPane green_10;
    @FXML
    StackPane green_11;
    @FXML
    StackPane green_12;
    @FXML
    StackPane green_13;
    @FXML
    StackPane green_14;
    @FXML
    StackPane green_15;
    @FXML
    StackPane green_16;
    @FXML
    StackPane green_17;

    @FXML
    StackPane red_0;
    @FXML
    StackPane red_1;
    @FXML
    StackPane red_2;
    @FXML
    StackPane red_3;
    @FXML
    StackPane red_4;
    @FXML
    StackPane red_5;
    @FXML
    StackPane red_6;
    @FXML
    StackPane red_7;
    @FXML
    StackPane red_8;
    @FXML
    StackPane red_9;
    @FXML
    StackPane red_10;
    @FXML
    StackPane red_11;
    @FXML
    StackPane red_12;
    @FXML
    StackPane red_13;
    @FXML
    StackPane red_14;
    @FXML
    StackPane red_15;
    @FXML
    StackPane red_16;
    @FXML
    StackPane red_17;

    //-------------------------------------NAME AND SCORE LABEL--------------------------------------
    @FXML
    Label redScore;
    @FXML
    Label yellowScore;
    @FXML
    Label greenScore;
    @FXML
    Label blueScore;

    @FXML
    Label redPlayer;
    @FXML
    Label yellowPlayer;
    @FXML
    Label greenPlayer;
    @FXML
    Label bluePlayer;

    //------------------------------------LEADER BOARD------------------------------------------------
    @FXML
    private Label player1 = new Label();
    @FXML
    private Label score1 = new Label();
    @FXML
    private Label player2 = new Label();
    @FXML
    private Label score2 = new Label();
    @FXML
    private Label player3 = new Label();
    @FXML
    private Label score3 = new Label();
    @FXML
    private Label player4 = new Label();
    @FXML
    private Label score4 = new Label();


    private Stage leaderBoard = new Stage();
    private PauseScene pauseScene;

    public void initialize() {
        fillHorse();
        addPathHandler();
        addNestHandler();
    }

    private void addPathHandler() {
        String[] colors = {"BLUE", "YELLOW", "GREEN", "RED"};
        for (String color: colors) {
            for (int i = 0; i < 18; i++) {
                int finalI = i;
                getPath(color, i).setOnMouseClicked(e -> {
                    try {
                        GameController.getInstance().setClickedHorsePathViewId(color + "_" + finalI);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            }
        }
    }

    private void addNestHandler() {
        blueNest.setOnMouseClicked(e -> {
            try {
                GameController.getInstance().setClickedHorsePathViewId("BLUE");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        yellowNest.setOnMouseClicked(e -> {
            try {
                GameController.getInstance().setClickedHorsePathViewId("YELLOW");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        greenNest.setOnMouseClicked(e -> {
            try {
                GameController.getInstance().setClickedHorsePathViewId("GREEN");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        redNest.setOnMouseClicked(e -> {
            try {
                GameController.getInstance().setClickedHorsePathViewId("RED");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void fillHorse() {
        String[] colors = {"Blue", "Yellow", "Green", "Red"};
        AnchorPane[] nests = {blueNest, yellowNest, greenNest, redNest};
        for (int colorIndex = 0; colorIndex < 4; colorIndex++) {
            for (int i = 0; i < 4; i++) {
                FileInputStream horseStream = null;
                try {
                    horseStream = new FileInputStream("src/view/horsePics/" + colors[colorIndex] + "Horse.png");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Image horseImage = new Image(horseStream);
                ImageView horseImageView = new ImageView(horseImage);
                horseImageView.setFitWidth(20);
                horseImageView.setFitHeight(30);
                horseImageView.setId(colors[colorIndex].toLowerCase());
                addToNest(nests[colorIndex], horseImageView);
            }
        }
    }

    private void addToNest(AnchorPane nest, ImageView horseImageView) {
        GridPane horseGrid = (GridPane) nest.getChildren().get(0);
        int size = horseGrid.getChildren().size();
        int column;
        int row;
        switch (size) {
            case 0:
                column = 0;
                row = 0;
                break;
            case 1:
                column = 1;
                row = 0;
                break;
            case 2:
                column = 0;
                row = 1;
                break;
            case 3:
                column = 1;
                row = 1;
                break;
            default:
                throw new IllegalStateException();
        }

        horseGrid.add(horseImageView, column, row);
    }

    public void summon(String color) {
        GridPane nest = getNest(color);
        ImageView horse = (ImageView) nest.getChildren().remove(nest.getChildren().size() - 1);
        StackPane homeArrival = null;
        switch (color) {
            case "BLUE":
                homeArrival = blue_0;
                break;
            case "YELLOW":
                homeArrival = yellow_0;
                break;
            case "GREEN":
                homeArrival = green_0;
                break;
            case "RED":
                homeArrival = red_0;
                break;
        }
        homeArrival.getChildren().add(horse);
    }

    private GridPane getNest(String color) {
        AnchorPane nest = null;
        switch (color) {
            case "BLUE":
                nest = blueNest;
                break;
            case "YELLOW":
                nest = yellowNest;
                break;
            case "GREEN":
                nest = greenNest;
                break;
            case "RED":
                nest = redNest;
                break;
        }

        return (GridPane) nest.getChildren().get(0);
    }

    public void move (String start, String finish) {
        String[] startParts = start.split("_");
        String[] finishParts = finish.split("_");
        move(startParts[0], Integer.parseInt(startParts[1]), finishParts[0], Integer.parseInt(finishParts[1]));
    }

    private void move(String start, int idStart, String finish, int idFinish) {
        StackPane startPath = getPath(start, idStart);
        ImageView horse = (ImageView) startPath.getChildren().remove(0);
        StackPane finishPath = getPath(finish, idFinish);
        if (finishPath.getChildren().size() > 0) {
            ImageView kickedHorse = (ImageView) finishPath.getChildren().remove(0);
            switch(kickedHorse.getId()) {
                case "blue":
                    addToNest(blueNest, kickedHorse);
                    break;
                case "yellow":
                    addToNest(yellowNest, kickedHorse);
                    break;
                case "green":
                    addToNest(greenNest, kickedHorse);
                    break;
                case "red":
                    addToNest(redNest, kickedHorse);
                    break;
            }
        }
        finishPath.getChildren().add(horse);

    }

    private StackPane getPath(String color, int id) {
        switch (color) {
            case "BLUE":
                switch (id) {
                    case 0: return blue_0;
                    case 1: return blue_1;
                    case 2: return blue_2;
                    case 3: return blue_3;
                    case 4: return blue_4;
                    case 5: return blue_5;
                    case 6: return blue_6;
                    case 7: return blue_7;
                    case 8: return blue_8;
                    case 9: return blue_9;
                    case 10: return blue_10;
                    case 11: return blue_11;
                    case 12: return blue_12;
                    case 13: return blue_13;
                    case 14: return blue_14;
                    case 15: return blue_15;
                    case 16: return blue_16;
                    case 17: return blue_17;
                }
            case "YELLOW":
                switch (id) {
                    case 0: return yellow_0;
                    case 1: return yellow_1;
                    case 2: return yellow_2;
                    case 3: return yellow_3;
                    case 4: return yellow_4;
                    case 5: return yellow_5;
                    case 6: return yellow_6;
                    case 7: return yellow_7;
                    case 8: return yellow_8;
                    case 9: return yellow_9;
                    case 10: return yellow_10;
                    case 11: return yellow_11;
                    case 12: return yellow_12;
                    case 13: return yellow_13;
                    case 14: return yellow_14;
                    case 15: return yellow_15;
                    case 16: return yellow_16;
                    case 17: return yellow_17;
                }
            case "GREEN":
                switch (id) {
                    case 0: return green_0;
                    case 1: return green_1;
                    case 2: return green_2;
                    case 3: return green_3;
                    case 4: return green_4;
                    case 5: return green_5;
                    case 6: return green_6;
                    case 7: return green_7;
                    case 8: return green_8;
                    case 9: return green_9;
                    case 10: return green_10;
                    case 11: return green_11;
                    case 12: return green_12;
                    case 13: return green_13;
                    case 14: return green_14;
                    case 15: return green_15;
                    case 16: return green_16;
                    case 17: return green_17;
                }
            case "RED":
                switch (id) {
                    case 0: return red_0;
                    case 1: return red_1;
                    case 2: return red_2;
                    case 3: return red_3;
                    case 4: return red_4;
                    case 5: return red_5;
                    case 6: return red_6;
                    case 7: return red_7;
                    case 8: return red_8;
                    case 9: return red_9;
                    case 10: return red_10;
                    case 11: return red_11;
                    case 12: return red_12;
                    case 13: return red_13;
                    case 14: return red_14;
                    case 15: return red_15;
                    case 16: return red_16;
                    case 17: return red_17;
                }
        }

        throw new IllegalStateException();
    }

    public void pauseGame() {
        pauseScene = new PauseScene();

        pauseScene.getButtons()[0].setOnMouseClicked(e -> {
            try {
                GameController.getInstance().playNewGame();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        pauseScene.getButtons()[1].setOnMouseClicked(e -> mainBoard.getChildren().remove(pauseScene));
        pauseScene.getButtons()[2].setOnMouseClicked(e -> {
            try {
                GameController.getInstance().playGame();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        pauseScene.getButtons()[3].setOnMouseClicked(e -> {
            try {
                GameController.getInstance().exit();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        mainBoard.getChildren().add(pauseScene);
        System.out.println("Game paused");
    }

    public void setRedScore(int score) {
        redScore.setText("Score: " + score);
    }

    public void setBlueScore(int score) {
        blueScore.setText("Score: " + score);
    }

    public void setGreenScore(int score) {
        greenScore.setText("Score: " + score);
    }

    public void setYellowScore(int score) {
        yellowScore.setText("Score: " + score);
    }

    public void setRedName(String name) {
        redPlayer.setText(name);
    }

    public void setGreenName(String name) {
        greenPlayer.setText(name);
    }

    public void setBlueName(String name) {
        bluePlayer.setText(name);
    }

    public void setYellowPlayer(String name) {
        yellowPlayer.setText(name);
    }

    //----------------------------------LEADER BOARD---------------------------------------

    private Button[] buttons = new Button[2];
    private String[] names = {"Play new game", "Play next game"};

    public void setLeaderBoard() throws IOException {
        System.out.println("end game?");
        leaderBoard = new Stage();
        VBox root = FXMLLoader.load(getClass().getResource("LeaderBoard.fxml"));
        HBox subPane = new HBox();

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button(names[i]);
            buttons[i].setStyle("-fx-background-color: lightblue");
            buttons[i].setTextFill(Color.WHITE);
            buttons[i].setPrefSize(130, 40);
        }
        subPane.getChildren().addAll(buttons[0], buttons[1]);
        subPane.setAlignment(Pos.CENTER);
        subPane.setSpacing(15);
        subPane.setStyle("-fx-background-color: white");
        root.getChildren().add(subPane);

        setNewGameBt(buttons[0], leaderBoard);
        setNextGameBt(buttons[1], leaderBoard);

        leaderBoard.setScene(new Scene(root, 300, 500));
        leaderBoard.setTitle("Leader Board");
        leaderBoard.show();
    }

    private void setNewGameBt(Button button, Stage stage) {
        button.setOnMouseClicked(e -> {
            try {
                stage.close();
                GameController.getInstance().playNewGame();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void setNextGameBt(Button button, Stage stage) {
        button.setOnMouseClicked(e -> {
            try {
                stage.close();
                GameController.getInstance().playGame();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void setPlayer1(String name, int score) {
        player1.setText(name);
        score1.setText(Integer.toString(score));
    }

    public void setPlayer2(String name, int score) {
        player2.setText(name);
        score2.setText(Integer.toString(score));
    }

    public void setPlayer3(String name, int score) {
        player3.setText(name);
        score3.setText(Integer.toString(score));
    }

    public void setPlayer4(String name, int score) {
        player4.setText(name);
        score4.setText(Integer.toString(score));
    }


}
