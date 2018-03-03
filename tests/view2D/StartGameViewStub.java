import engine.GameHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view2D.GameViewController2D;

import java.nio.file.Paths;

public class StartGameViewStub extends Application{

    public GameHandler gameHandler;
    public GameViewController2D gameView;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view2D/gameview.fxml"));
        primaryStage.setTitle("Zombie Game");
        primaryStage.setScene(new Scene(root, 847, 593));
        primaryStage.show();

        FXMLLoader fxmlLoader = new FXMLLoader();
        Pane p = fxmlLoader.load(getClass().getResource("view2D/gameview.fxml").openStream());
        gameView = (GameViewController2D) fxmlLoader.getController();
        gameHandler = gameView.getGameHandler();

    }





      public void go(String[] args){
          launch(args);
      }

}



