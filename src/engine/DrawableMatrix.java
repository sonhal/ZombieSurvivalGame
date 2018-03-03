package engine;

public class DrawableMatrix {
    public DrawableTile[][] matrix;

    int direction = 0;

    DrawableMatrix(World world, Tile baseTile, int widthRadiX, int heightRadiY) {
        matrix = generateDrawable(world, baseTile, widthRadiX, heightRadiY);
    }

    public DrawableTile[][] generateDrawable(World world, Tile baseTile, int radiX, int radiY){
        Tile currentTile = baseTile;
        DrawableTile[][] drawableWorld = new DrawableTile[radiX*2][radiY*2];
        for (int i = 0; i < radiX; i++){
            currentTile = currentTile.left;
        }
        for (int i = 0; i < radiY; i++){
            currentTile = currentTile.up;
        }
        Tile rowBeggining = currentTile;
        for (int y = 0; y < radiY * 2; y++){

            for (int x = 0; x < radiX * 2; x++){
                drawableWorld[x][y] = new DrawableTile(currentTile.getGameObject(), currentTile.getItem());
                currentTile = currentTile.right;
            }
            rowBeggining = rowBeggining.down;
            currentTile = rowBeggining;
        }
        return drawableWorld;
    }
}
