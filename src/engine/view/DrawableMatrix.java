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

    /**
     * Copies a simplified version of the tiles within a defined radius arount a tile.
     * Each time the view asks for a new frame this method is run copying the area around the player to a array so it can be rendered on the screen.
     * @param world The world object of tiles.
     * @param baseTile The tile in the center of the area which will be drawn
     * @param radiX How many tiles to each side of the center the method will include in the array
     * @param radiY How many tiles to up and down from the center the method will include in the array
     * @return
     */
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
                    drawableWorld[y][x] = new DrawableTile(currentTile.getGameObject(),
                            currentTile.getItem(), currentTile.getSprite(), currentTile.getParticleEffect());
                    if(currentTile != currentTile.getRight()) {
                        currentTile = currentTile.getRight();
                    }else{
                        lineEndReached = true;
                    }
                }else{
                    drawableWorld[y][x] = new DrawableTile(null, null, new Sprite(29), null);
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
