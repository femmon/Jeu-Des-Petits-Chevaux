package view;

import javafx.scene.layout.StackPane;
import model.Horse;

public class NestView extends StackPane {

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

//    private void getHorse() {
//        // returns horse count
//    }
    
    public void getHorseCount() {
        System.out.println("horseCount = " + horseCount);
    }
}
