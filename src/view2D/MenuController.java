package view2D;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MenuController implements Initializable{

    @FXML
    public Label menuTitle;
    public Button play;
    public Label infoText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        play.setOnAction((event) -> {
            // play game button pressed
            try {
                startGame();
            }catch (IOException err){
                //show error to player
                showInfo(err.toString());
            }

        });
    }

    private void startGame() throws IOException{
        System.out.println("Game started!");
        Stage stage = (Stage)play.getScene().getWindow();
        Scene scene = (Scene)play.getScene();
        Parent newRoot = FXMLLoader.load(getClass().getResource("gameview.fxml"));
   //     stage.setScene(new Scene(newRoot, 1062, 752));
        scene.setRoot(newRoot);
    }

    private void showInfo(String info){
        infoText.setText(info);
    }
}
