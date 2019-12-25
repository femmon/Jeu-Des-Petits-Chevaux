import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.util.ArrayList;

import static model.Color.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        ArrayList<Player> a = new ArrayList<>();
        Color[] colors = {RED, BLUE, YELLOW, GREEN};
        for (int i = 0; i < 4; i++) {
            Player player = new Player();
            player.setPlayerSide(colors[i]);
            player.setPlayerType(PlayerType.HUMAN);
            a.add(player);
        }
        Board b = new Board(a);
        b.printState();
        b.summon(RED);
        b.printState();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
