package view2D;

import engine.*;
import engine.composites.Sprite;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import view.GameViewController;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

import java.awt.*;
import java.awt.event.ComponentListener;
import java.net.URL;
import java.util.EventListener;
import java.util.ResourceBundle;
import java.util.TimerTask;

import static javafx.scene.paint.Color.*;


public class GameViewController2D implements GameViewController, Initializable{

    GameHandler gameHandler;
    DrawableTile[][] drawableMatrix;
    Renderer renderer;
    Scene scene;

    @FXML
    private
    Canvas gameCanvas;


    @FXML
    public
    MenuItem settingsMenu;

    @FXML
    public
    Pane settings, shaderSetting, motherPane;

    @FXML
    public
    Rectangle greenBar;
    
    @Override
    public void updateDrawableState(DrawableTile[][] drawableMatrix) {
        this.drawableMatrix = drawableMatrix;
    }

    public void openMenu () {
        settings.setVisible(true);
        settings.setManaged(true);
        shaderSetting.setVisible(false);
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
    public void initializeGameEnv() {
        System.out.println("Game controller active");
        settings.setVisible(false);
        shaderSetting.setVisible(false);
        settings.setManaged(false);
        gameCanvas.isResizable();
        gameCanvas.heightProperty().bind(motherPane.heightProperty());
        gameCanvas.widthProperty().bind(motherPane.widthProperty());
        gameCanvas.widthProperty().addListener(((observable, oldValue, newValue) -> {
            System.out.println("width resize");
           // setStubDrawableMatrix(gameHandler, (int)gameCanvas.getWidth(),(int)gameCanvas.getHeight()/2);
        }));
        gameCanvas.heightProperty().addListener(((observable, oldValue, newValue) -> {
            System.out.println("height resize");
            //setStubDrawableMatrix(gameHandler, (int)gameCanvas.getWidth()%2,(int)gameCanvas.getHeight()%2);

        }));
        renderer = new Renderer(gameCanvas);
        gameCanvas.getGraphicsContext2D().setFill(BLACK);
        gameCanvas.getGraphicsContext2D().fillRect(0,0,gameCanvas.getWidth(),gameCanvas.getHeight());
        updateDrawableState(setStubDrawableMatrix(gameHandler, 10, 10));
        gameHandler.setObjectInWorld();
    }

    public void startGameloop(){
        runViewTick();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameHandler = new GameHandler(this);
        initializeGameEnv();

        startGameloop();
        setEventHandlers();


    }

    public void runViewTick(){
        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount( Timeline.INDEFINITE );

        final long timeStart = System.currentTimeMillis();

        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.017),                // 60 FPS
                ae -> update());

        gameLoop.getKeyFrames().add( kf );
        gameLoop.play();
    }

    public DrawableTile[][] setStubDrawableMatrix(GameHandler gh, int diameterX, int diameterY){
        DrawableTile[][] drawableMatrix = gh.getDrawableMatrix(diameterX, diameterY).matrix;
        /*try {

            drawableMatrix[1][1].setGameObject(GameObjectFactory.create());
            drawableMatrix[5][5].setGameObject(new Item(new Sprite(2), 10));
            drawableMatrix[10][10].setGameObject(new Item(new Sprite(2), 10));


        }catch (Exception e){
            e.printStackTrace();
        }*/
        return drawableMatrix;

    }


    public GameHandler getGameHandler() {
        return gameHandler;
    }


    private void setEventHandlers(){

        Platform.runLater(new Runnable() {
            public void run() {
                gameCanvas.getScene().setOnKeyPressed(e -> {
                    if (e.getCode() == KeyCode.UP) {
                        System.out.println("Up key was pressed");
                        gameHandler.sendEvent(ActionEvent.MOVE_UP);
                    }
                    if (e.getCode() == KeyCode.DOWN) {
                        System.out.println("Down key was pressed");
                        gameHandler.sendEvent(ActionEvent.MOVE_DOWN);
                    }
                    if (e.getCode() == KeyCode.LEFT) {
                        System.out.println("Left key was pressed");
                        gameHandler.sendEvent(ActionEvent.MOVE_LEFT);
                    }
                    if (e.getCode() == KeyCode.RIGHT) {
                        System.out.println("Right key was pressed");
                        gameHandler.sendEvent(ActionEvent.MOVE_RIGHT);
                    }
                });
            }
        });
    }

    private void update(){
        drawableMatrix = gameHandler.getDrableWorld();
        renderer.render(drawableMatrix);
    }
    //work in progress
    private void changeCanvasSize(){
        double canvasWidth = 0;
        double canvasHeight = 0;
        canvasWidth = gameCanvas.getWidth();
        canvasHeight = gameCanvas.getHeight();
    }
    @FXML
    public void HealthBar () {
        greenBar.setWidth(200.0);

    }


}
