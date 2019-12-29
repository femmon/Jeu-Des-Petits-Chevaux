package controller;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameController {

    Node chosenNode;
    VBox horzHomePaths;
    HBox vertHomePaths;
    private GameController controller;

    public GameController getInstance() {
        if (controller == null)
            controller = new GameController();
        return controller;
    }

//    public void onClickTest(MouseEvent event) {
//        chosenNode = (Node) event.getSource();
//        if (chosenNode instanceof VBox) {
//            System.out.println("VBox is clicked");
//        }
//    }
}
