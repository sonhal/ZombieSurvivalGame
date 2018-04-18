package view2D;

import engine.services.audio.AudioPlayer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("gamemenu.fxml"));
        primaryStage.setTitle("Zombie Game");
        primaryStage.setScene(new Scene(root, 847, 593));
        primaryStage.show();
    }

    @Override
    public void stop() {
        //Make sure that the AudioPlayer has properly shutdown
        AudioPlayer.getInstance().shutdown();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
