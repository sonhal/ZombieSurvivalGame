package view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameViewController implements Initializable{

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
    public void initialize(URL location, ResourceBundle resources) {

        settings.setVisible(false);
        shaderSetting.setVisible(false);

        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(2,3,200,200);
    }

}
