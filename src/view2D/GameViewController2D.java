package view2D;

import engine.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import view.GameViewController;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class GameViewController2D implements GameViewController, Initializable{

    GameHandler gameHandler;
    Tile[][] drawableMatrix;
    GraphicsContext gc;
    SpriteTranslationHandler spriteTranslator;

    @FXML
    private
    Canvas gameCanvas;

    @Override
    public void updateDrawableState(Tile[][] drawableMatrix) {
        this.drawableMatrix = drawableMatrix;
    }

    @Override
    public void startGame() {

        System.out.println("Game controller active");
        gameHandler = new GameHandler(this);
        setStubDrawableMatrix();



        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,gameCanvas.getWidth(),gameCanvas.getHeight());

        runViewTick();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        spriteTranslator = new SpriteTranslationHandler();
        gc = gameCanvas.getGraphicsContext2D();
        startGame();
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

        gc.clearRect(0,0,512,512);
        int yAxisOffset = 0;
        for (Tile[] tileRow: this.drawableMatrix) {
            int xAxisOffset = 0;
            for (Tile tile: tileRow) {

                drawTile(tile, xAxisOffset, yAxisOffset);
                xAxisOffset += StaticFields.DRAW_ENTITY_SIZE;
            }
            yAxisOffset += StaticFields.DRAW_ENTITY_SIZE;



        }
    }

    public void setStubDrawableMatrix(){
        this.drawableMatrix = gameHandler.getDrawableMatrix(10).drawableMatrix;
        try {
            for (Tile[] tiles: this.drawableMatrix
                 ) {
                for (Tile tile: tiles
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

    }

    public void drawTile(Tile tile, int xPos, int yPos){


        GameObject gameObject;
        try {
            gameObject = tile.getGameObject();
        }catch (NullPointerException er){
            gameObject = null;
        }
        if(gameObject == null ){
            gc.setFill(Color.SANDYBROWN);
            gc.fillRect(xPos,yPos, StaticFields.DRAW_ENTITY_SIZE,StaticFields.DRAW_ENTITY_SIZE);
        }
        else{
            gc.drawImage(spriteTranslator.getImage(gameObject.sprite),
                    xPos, yPos, StaticFields.DRAW_ENTITY_SIZE, StaticFields.DRAW_ENTITY_SIZE);

        }
    }

}
