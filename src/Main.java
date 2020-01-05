import controller.GameController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
     // there is ONLY one controller, the controller instance
     @Override public void start(Stage primaryStage) throws IOException { GameController.getInstance().update(); }

    public static void main(String[] args) {
        launch(args);
    } // reincarnation of fps

}

