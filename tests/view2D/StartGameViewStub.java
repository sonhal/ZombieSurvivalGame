package view2D;

import engine.controllers.GameHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartGameViewStub extends Application{

    public GameHandler gameHandler;
    public GameViewController2D gameView;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view2D/gameview2D.fxml"));
        Parent root = fxmlLoader.load();


        gameView = fxmlLoader.getController();
        if(gameView == null){
            System.out.println("Null controller");
        }
        else {
            System.out.println("Good controller");
        }
        gameHandler = gameView.getGameHandler();

        primaryStage.setTitle("Zombie Game");
        primaryStage.setScene(new Scene(root, 847, 593));
        primaryStage.show();




    }





      public void go(String[] args){
          launch(args);
      }

}



