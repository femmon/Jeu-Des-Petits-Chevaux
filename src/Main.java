import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.util.ArrayList;
import java.util.Scanner;

import static model.Color.*;

public class Main extends Application {

    /**
     * Posible command:
     *   "E": exit
     *   "R": summon a RED horse
     *   "B": summon a BLUE horse
     *   "Y": summon a YELLOW horse
     *   "G": summon a GREEN horse
     *   "C i m": to move the horse "C-i" "m" steps forward.
     *     `C` is one of the 4 initial of color in uppercase (R, B, Y, G),
     *     `i` is the id of horse: 0 - 3, `m` is the number of steps to move
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
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
        Scanner input = new Scanner(System.in);
        while (true) {
            String command = input.nextLine();
            String[] args = command.split(" ");

            if (args.length == 1){
                switch (args[1]) {
                    case "E":
                        return;
                    case "R":
                        b.summon(RED);
                        break;
                    case "G":
                        b.summon(GREEN);
                        break;
                    case "B":
                        b.summon(BLUE);
                        break;
                    case "Y":
                        b.summon(YELLOW);
                        break;
                    default:
                        continue;
                }
                b.printState();
            } else if (args.length == 3){
                Color color;
                switch (args[0]) {
                    case "R":
                        color = RED;
                        break;
                    case "G":
                        color = GREEN;
                        break;
                    case "B":
                        color = BLUE;
                        break;
                    case "Y":
                        color = YELLOW;
                        break;
                    default:
                        continue;
                }

                int id = Integer.parseInt(args[1]);
                if (id < 0 || id > 3) continue;

                int move = Integer.parseInt(args[2]);
                if (move < 1 || move > 6) continue;

                b.move(color, id, move);

                b.printState();
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
