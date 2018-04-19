package view2D;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MenuController implements Initializable{

    @FXML
    public Label menuTitle;
    public Button play;
    public Button load;
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

        load.setOnAction((event) -> {
            // play game button pressed
            try {
                loadGame();
            }catch (IOException err){
                //show error to player
                showInfo(err.toString());
            }

        });
    }

    private void startGame() throws IOException{
        System.out.println("Game started!");
        Stage stage = (Stage)play.getScene().getWindow();
        //Parent newRoot = FXMLLoader.load(getClass().getResource("gameview.fxml"));
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "gameviewV2.fxml"
                )
        );
        stage.setScene(new Scene(loader.load(), StaticFields.WINDOW_WIDTH, StaticFields.WINDOW_HEIGHT));
        GameViewController2D controller =
                loader.<GameViewController2D>getController();
        controller.startNewGame();
    }

    private void loadGame() throws IOException{
        System.out.println("Game started!");
        Stage stage = (Stage)play.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "gameviewV2.fxml"
                )
        );
        stage.setScene(new Scene(loader.load(), StaticFields.WINDOW_WIDTH, StaticFields.WINDOW_HEIGHT));
        GameViewController2D controller =
                loader.<GameViewController2D>getController();
        controller.startLoadGame();
    }



    private void showInfo(String info){
        infoText.setText(info);
    }
}
