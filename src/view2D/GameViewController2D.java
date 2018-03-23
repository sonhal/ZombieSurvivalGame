package view2D;

import engine.controllers.ActionEvent;
import engine.controllers.GameHandler;
import engine.view.DrawableTile;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.GameViewController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class GameViewController2D implements GameViewController, Initializable{

    GameHandler gameHandler;
    DrawableTile[][] drawableMatrix;
    Renderer renderer;
    Scene scene;
    Timeline gameLoop;



    @FXML
    private
    Canvas gameCanvas;


    @Override
    public void updateDrawableState(DrawableTile[][] drawableMatrix) {
        this.drawableMatrix = drawableMatrix;
    }

    @Override
    public void initializeGameEnv() {
        System.out.println("Game controller active");
        gameCanvas.setHeight(StaticFields.CANVAS_SIZE);
        gameCanvas.setWidth(StaticFields.CANVAS_SIZE);

        System.out.println("Game controller active 2");

        renderer = new Renderer(gameCanvas);
        gameCanvas.getGraphicsContext2D().setFill(Color.BLACK);
        gameCanvas.getGraphicsContext2D().fillRect(0,0,gameCanvas.getWidth(),gameCanvas.getHeight());
        System.out.println("Game controller active 3");
        //gameHandler.setObjectInWorld();
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
        gameLoop = new Timeline();
        gameLoop.setCycleCount( Timeline.INDEFINITE );

        final long timeStart = System.currentTimeMillis();

        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.017),                // 60 FPS
                ae -> update());

        gameLoop.getKeyFrames().add( kf );
        gameLoop.play();
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
                    if (e.getCode() == KeyCode.SPACE) {
                        System.out.println("Space key was pressed");
                        gameHandler.sendEvent(ActionEvent.ATTACK_UP);
                    }
                });
            }
        });
    }

    private void update(){
        drawableMatrix = gameHandler.getDrawableWorld();
        renderer.render(drawableMatrix);
    }

    private void stopGameLoop(){
        gameLoop.stop();
    }

    public void goToMenu() throws IOException {
        stopGameLoop();
        System.out.println("Game ended!");
        Stage stage = (Stage)gameCanvas.getScene().getWindow();
        Parent newRoot = FXMLLoader.load(getClass().getResource("gamemenu.fxml"));
        stage.setScene(new Scene(newRoot, 847, 593));
    }


}
