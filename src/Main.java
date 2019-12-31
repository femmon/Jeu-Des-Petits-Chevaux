import controller.GameController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        GameController controller = GameController.getInstance();
        controller.update();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

