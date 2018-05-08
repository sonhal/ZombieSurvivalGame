package view2D;

import engine.view.DrawableTile;
import engine.entities.Sprite;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;


public class Renderer {
    private Canvas canvas;
    private GraphicsContext gc;
    private SpriteTranslationHandler spriteTranslator;
    private double enitySize;
    private int drawHealth = 100;

    public Renderer(Canvas canvas){
        this.canvas = canvas;
        gc = canvas.getGraphicsContext2D();
        spriteTranslator = new SpriteTranslationHandler();
    }



    public void render(DrawableTile[][] drawableMatrix){
        setEntitySize(drawableMatrix);
        gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight() );
        int yAxisOffset = 0;
        for (DrawableTile[] tileRow: drawableMatrix) {
            int xAxisOffset = 0;
            for (DrawableTile tile: tileRow) {

                drawTile(tile, xAxisOffset, yAxisOffset);
                xAxisOffset += enitySize;
            }
            yAxisOffset += enitySize;

        }
    }



    private void drawTile(DrawableTile tile, int xPos, int yPos){

        drawOnCanvas(tile.getSprite(), xPos, yPos);

        if(tile.getItem() != null){
            drawOnCanvas(tile.getItem().getSprite(), xPos, yPos);
        }
        if(tile.getGameObject() != null){
            tile.getGameObject().getSprite().forEach(sprite -> drawOnCanvas(sprite, xPos, yPos));
        }

    }

    private void drawOnCanvas(Sprite sprite, int xPos, int yPos){
        gc.drawImage(spriteTranslator.getSpriteImage(sprite),
                xPos, yPos, enitySize, enitySize);
    }

    public void setEntitySize(DrawableTile[][] drawableMatrix){

        if (drawableMatrix != null) {
            enitySize = canvas.getWidth() / drawableMatrix.length;
        }
        else enitySize = 0;
    }

}
