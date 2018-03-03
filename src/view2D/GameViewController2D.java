package view2D;

import engine.DrawableMatrix;
import engine.GameHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import view.GameViewController;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import java.net.URL;
import java.util.EventListener;
import java.util.ResourceBundle;

public class GameViewController2D implements GameViewController, Initializable{

    GameHandler gameHandler;

    @FXML
    private
    Canvas gameCanvas;
    @FXML
    public
    Pane settings;

    @FXML
    public
    Pane shaderSetting;

    @FXML
    public
    MenuItem settingsMenu;


    @Override
    public void updateDrawableState(DrawableMatrix drawableMatrix) {

    }

    public void openMenu () {
        settings.setVisible(true);
        settings.setManaged(true);
        shaderSetting.setVisible(true);
    }

    public void closeMenu () {
        settings.setVisible(false);
        settings.setManaged(false);
        shaderSetting.setVisible(false);
    }


    @FXML
    public void keyHandler (KeyEvent event){
        if(event.getCode() == KeyCode.KP_UP){
            System.out.println("Key pressed");
            event.consume();
        }
    }

    @Override
    public void startGame() {
        System.out.println("Game controller active");
        gameHandler = new GameHandler(this);
        settings.setVisible(false);
        shaderSetting.setVisible(false);
        settings.setManaged(false);

        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(2,3,200,200);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        startGame();
    }

}
