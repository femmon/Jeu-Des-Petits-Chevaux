package view;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import model.Horse;

public class NestView {
    private StackPane nest;
    private int horseCount = 0;

     public NestView(StackPane nest) {
          this.nest = nest;
      }

     public void addHorse() {
         horseCount++;
     }

    public void removeHorse() {
         horseCount++;
      }
    
    public void getHorseCount() {
        System.out.println("horseCount = " + horseCount);
    }

    public ObservableList<Node> getNestContents() {
        return nest.getChildren();
    }
}
