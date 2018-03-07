package view2D;

import engine.*;
import engine.composites.Sprite;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import view.GameViewController;

import java.net.URL;
import java.util.ResourceBundle;


public class GameViewController2D implements GameViewController, Initializable{

    GameHandler gameHandler;
    DrawableTile[][] drawableMatrix;
    Renderer renderer;



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



        renderer = new Renderer(gameCanvas);
        gameCanvas.getGraphicsContext2D().setFill(Color.BLACK);
        gameCanvas.getGraphicsContext2D().fillRect(0,0,gameCanvas.getWidth(),gameCanvas.getHeight());
        updateDrawableState(setStubDrawableMatrix(gameHandler));
        setEventHandlers();
        System.out.println("EventHandler connected");
    }

    public void startGameloop(){
        runViewTick();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameHandler = new GameHandler(this);
        initializeGameEnv();

        startGameloop();


    }

    public void runViewTick(){
        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount( Timeline.INDEFINITE );

        final long timeStart = System.currentTimeMillis();

        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.017),                // 60 FPS
                ae -> renderer.render(drawableMatrix));

        gameLoop.getKeyFrames().add( kf );
        gameLoop.play();
    }

    public DrawableTile[][] setStubDrawableMatrix(GameHandler gh){
        DrawableTile[][] drawableMatrix = gh.getDrawableMatrix(10).matrix;
        /*try {

            drawableMatrix[1][1].setGameObject(GameObjectFactory.create());
            drawableMatrix[5][5].setGameObject(new Item(new Sprite(2), 10));
            drawableMatrix[10][10].setGameObject(new Item(new Sprite(2), 10));


        }catch (Exception e){
            e.printStackTrace();
        }*/
        return drawableMatrix;

    }

    public void setEventHandlers(){

        gameCanvas.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println(event.getCode());
                System.out.println("Player pressed key");
                gameHandler.sendEvent(ActionEvent.MOVE_UP);
            }
        });
    }



}
