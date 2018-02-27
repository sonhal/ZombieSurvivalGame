package view2D;

import engine.DrawableMatrix;
import engine.GameHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import view.GameViewController;

import java.net.URL;
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

    @Override
    public void updateDrawableState(DrawableMatrix drawableMatrix) {

    }

    @Override
    public void startGame() {
        gameHandler = new GameHandler(this);
        settings.setVisible(false);
        shaderSetting.setVisible(false);

        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(2,3,200,200);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startGame();
    }

}
