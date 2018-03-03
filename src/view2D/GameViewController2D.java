package view2D;

import engine.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import view.GameViewController;

import java.net.URL;
import java.util.ResourceBundle;


public class GameViewController2D implements GameViewController, Initializable{

    GameHandler gameHandler;
    DrawableTile[][] drawableMatrix;
    GraphicsContext gc;
    SpriteTranslationHandler spriteTranslator;

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

        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,gameCanvas.getWidth(),gameCanvas.getHeight());
        updateDrawableState(setStubDrawableMatrix(gameHandler));
    }

    public void startGameloop(){
        runViewTick();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        spriteTranslator = new SpriteTranslationHandler();
        gc = gameCanvas.getGraphicsContext2D();
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
                ae -> updateView());

        gameLoop.getKeyFrames().add( kf );
        gameLoop.play();
    }

    private void updateView(){

        gc.clearRect(3,3,StaticFields.CANVAS_SIZE - 3,StaticFields.CANVAS_SIZE - 3);
        int yAxisOffset = 0;
        for (DrawableTile[] tileRow: this.drawableMatrix) {
            int xAxisOffset = 0;
            for (DrawableTile tile: tileRow) {

                drawTile(tile, xAxisOffset, yAxisOffset);
                xAxisOffset += getEntitySize();
            }
            yAxisOffset += getEntitySize();



        }
    }



    public void drawTile(DrawableTile tile, int xPos, int yPos){


        GameObject gameObject;
        try {
            gameObject = tile.getGameObject();
        }catch (NullPointerException er){
            gameObject = null;
        }
        if(gameObject == null ){
            gc.setFill(Color.SANDYBROWN);
            gc.fillRect(xPos,yPos, getEntitySize(),getEntitySize());
        }
        else{
            gc.drawImage(spriteTranslator.getImage(gameObject.sprite),
                    xPos, yPos, getEntitySize(), getEntitySize());

        }
    }

    public GameHandler getGameHandler(){
        return gameHandler;
    }

    public DrawableTile[][] setStubDrawableMatrix(GameHandler gh){
        DrawableTile[][] drawableMatrix = gh.getDrawableMatrix(10).matrix;
        try {
            for (DrawableTile[] tiles: drawableMatrix
                    ) {
                for (DrawableTile tile: tiles
                        ) {
                    try{
                        tile.setGameObject(new Item(new Sprite(1), 10));
                    }
                    catch (NullPointerException er){
                        System.out.println("There was a null Tile here");
                    }
                }

            }
            drawableMatrix[1][1].setGameObject(new Item(new Sprite(2), 10));
            drawableMatrix[5][5].setGameObject(new Item(new Sprite(2), 10));
            drawableMatrix[10][10].setGameObject(new Item(new Sprite(2), 10));


        }catch (Exception e){
            e.printStackTrace();
        }
        return drawableMatrix;

    }

    public int getEntitySize(){

        if (drawableMatrix != null) return StaticFields.CANVAS_SIZE / drawableMatrix.length;
        else return 0;
    }

}
