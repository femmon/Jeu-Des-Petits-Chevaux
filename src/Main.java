import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import view.GameView;

import java.io.IOException;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/pachisi.fxml"));
        primaryStage.setTitle("Hello World");
        GameView gameview = new GameView((GridPane) root);
        Scene primaryScene = new Scene(root, 800, 800);
        primaryScene.getStylesheets().add(getClass().getResource("view/debug.css").toExternalForm());
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


//      Controller controller = Controller.getInstance();
//       controller.update();