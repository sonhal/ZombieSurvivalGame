package view2D;

import engine.gamestate.HighScoreData;
import engine.services.save.SaveGameHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class MenuController implements Initializable{

    @FXML
    public Label menuTitle;
    public Button play;
    public Button load;
    public Label infoText;
    public BorderPane borderPane;
    public VBox about;
    public VBox highscore;
    public TableView highScoreTable;
    public TableColumn highScoreDate;
    public TableColumn highScoreScore;

    /**
     * initialize. initializes game menu, controls onAction for buttons
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BackgroundImage myBI= new BackgroundImage(new Image("view2D/static/menuBG.jpg",600,600,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        borderPane.setBackground(new Background(myBI));
        loadInHighscoreData();

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
    /**
     * startGame. method builds scene for gameView
     */
    private void startGame() throws IOException{
        System.out.println("Game started!");
        Stage stage = (Stage)play.getScene().getWindow();
        //Parent newRoot = FXMLLoader.load(ge45tClass().getResource("gameview.fxml"));
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "gameview.fxml"
                )
        );
        stage.setScene(new Scene(loader.load(), StaticFields.WINDOW_WIDTH, StaticFields.WINDOW_HEIGHT));
        GameViewController2D controller =
                loader.<GameViewController2D>getController();
        controller.startNewGame(controller);
    }

    /**
     * loadGame. fetches loadsave, builds scene for gameView
     */
    private void loadGame() throws IOException{
        System.out.println("Game started!");
        Stage stage = (Stage)play.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "gameview.fxml"
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

    /**
     * toggleShowHighscore. sets highscore visible
     */
    public void toggleShowHighscore(){
        if(highscore.isVisible()){
            highscore.setVisible(false);
        }
        else {
            highscore.setVisible(true);
        }

    }

    public void toggleShowAbout(){
        if(about.isVisible()){
            about.setVisible(false);
        }
        else {
            about.setVisible(true);
        }
    }

    /**
     * loadInHighscoreData. recievs data to highscore display
     */
    public void loadInHighscoreData(){
        try {
            Optional<HighScoreData> highScoreData = SaveGameHandler.loadHighScore();
            if(highScoreData.isPresent()){
                highScoreDate.setCellValueFactory(
                        new PropertyValueFactory<HighScoreData.HighScoreEntry,String>("timePoint")
                );
                highScoreScore.setCellValueFactory(
                        new PropertyValueFactory<HighScoreData.HighScoreEntry,Integer>("score")
                );

                StringBuilder builder = new StringBuilder();
                ObservableList<HighScoreData.HighScoreEntry> obsList = FXCollections.observableArrayList();
                obsList.addAll( highScoreData.get().getHighscoreSet());
                highScoreTable.setItems(obsList);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
