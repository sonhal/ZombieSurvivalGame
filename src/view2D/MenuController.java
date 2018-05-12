package view2D;

import engine.services.save.SaveGameHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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
    public ImageView backGroundImage;
    public BorderPane borderPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BackgroundImage myBI= new BackgroundImage(new Image("view2D/static/menuBG.jpg",600,600,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        borderPane.setBackground(new Background(myBI));

        play.setOnAction((event) -> {
            // play game button pressed
            try {
                startGame();
            }catch (IOException err){
                //show error to player
                showInfo(err.toString());
            }

        });

        if(canLoadGame()){
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
        else {
            load.setDisable(true);
        }

    }

    private void startGame() throws IOException{
        System.out.println("Game started!");
        Stage stage = (Stage)play.getScene().getWindow();
        //Parent newRoot = FXMLLoader.load(ge45tClass().getResource("gameview.fxml"));
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "gameviewV2.fxml"
                )
        );
        stage.setScene(new Scene(loader.load(), StaticFields.WINDOW_WIDTH, StaticFields.WINDOW_HEIGHT));
        GameViewController2D controller =
                loader.<GameViewController2D>getController();
        controller.startNewGame(controller);
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
        controller.startLoadGame(controller);
    }

    private boolean canLoadGame(){
        return SaveGameHandler.isSaveFileValid();
    }

    private void showInfo(String info){
        //infoText.setText(info);
    }
}
