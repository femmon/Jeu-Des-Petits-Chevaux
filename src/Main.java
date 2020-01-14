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
import controller.GameController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
     // there is ONLY one controller, the controller instance
     @Override public void start(Stage primaryStage) throws IOException { GameController.getInstance().update(); }

     public static void main(String[] args) { launch(args);} // reincarnation of fps

}

