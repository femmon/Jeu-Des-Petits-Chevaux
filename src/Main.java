import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import view.GameView;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/pachisi.fxml"));
        primaryStage.setTitle("Hello World");
        GameView gameview = new GameView((GridPane) root);
        Scene primaryScene = new Scene(root, 1000, 1000);
        primaryScene.getStylesheets().add(getClass().getResource("view/debug.css").toExternalForm());
        primaryStage.setScene(primaryScene);
        primaryStage.show();

        System.out.println(gameview.getBlueNest().getChildren().get(0));
    }

    public static void main(String[] args) {
        launch(args);
    }
}


//      Controller controller = Controller.getInstance();
//       controller.update();