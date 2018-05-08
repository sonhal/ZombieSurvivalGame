package engine.view;

import engine.entities.gameobjects.Sprite;
import engine.world.Tile;
import engine.world.World;

import java.io.Serializable;

public class DrawableMatrix implements Serializable{
    public DrawableTile[][] matrix;

    int direction = 0;

    public DrawableMatrix(World world, Tile baseTile, int widthRadiX, int heightRadiY) {
        matrix = generateDrawable(world, baseTile, widthRadiX, heightRadiY);
    }

    public DrawableTile[][] generateDrawable(World world, Tile baseTile, int radiX, int radiY){
        Tile currentTile = baseTile;
        DrawableTile[][] drawableWorld = new DrawableTile[radiX*2][radiY*2];
        for (int i = 0; i < radiX; i++){
            currentTile = currentTile.getLeft();
        }
        for (int i = 0; i < radiY; i++){
            currentTile = currentTile.getUp();
        }
        Tile rowBeggining = currentTile;
        boolean bottomEndReached = false;
        for (int y = 0; y < radiY * 2; y++){
            boolean lineEndReached = false;
            for (int x = 0; x < radiX * 2; x++){
                if (lineEndReached == false && bottomEndReached == false){
                    drawableWorld[x][y] = new DrawableTile(currentTile.getGameObject(),
                            currentTile.getItem(), currentTile.getSprite());
                    if(currentTile != currentTile.getRight()) {
                        currentTile = currentTile.getRight();
                    }else{
                        lineEndReached = true;
                    }
                }else{
                    drawableWorld[x][y] = new DrawableTile(null, null, new Sprite(29));
                    currentTile = currentTile.getRight();
                }

            }
            if (rowBeggining != rowBeggining.getDown()) {
                rowBeggining = rowBeggining.getDown();
                currentTile = rowBeggining;
            }else{
                bottomEndReached = true;
            }
        }
        return drawableWorld;
    }
}
