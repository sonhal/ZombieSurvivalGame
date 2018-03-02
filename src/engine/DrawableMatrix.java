package engine;

public class DrawableMatrix {
    public Tile[][] matrix;

    int direction = 0;

    DrawableMatrix(World world, Tile baseTile, int widthRadiX, int heightRadiY) {
        matrix = generateDrawable(world, baseTile, widthRadiX, heightRadiY);
    }

    public Tile[][] generateDrawable(World world, Tile baseTile, int radiX, int radiY){
        Tile currentTile = baseTile;
        Tile[][] drawableWorld = new Tile[radiX*2][radiY*2];
        for (int i = 0; i < radiX; i++){
            currentTile = currentTile.left;
        }
        for (int i = 0; i < radiY; i++){
            currentTile = currentTile.up;
        }
        Tile rowBeggining = currentTile;
        for (int y = 0; y < radiY * 2; y++){

            for (int x = 0; x < radiX * 2; x++){
                drawableWorld[x][y] = currentTile;
                currentTile = currentTile.right;
            }
            rowBeggining = rowBeggining.down;
            currentTile = rowBeggining;
        }
        return drawableWorld;
    }

    public Tile[][] generateDrawableBySpiral(World world, Tile baseTile, int widthX, int heightY) {
        Tile[][] drawableMatrix = new Tile[widthX +100][heightY+100];
        Tile currentTile = baseTile;
        int x=0, y=0, dx = 0, dy = -1;
        int longest = Math.max(widthX,heightY);
        int maxNumberOfTiles = longest*longest;

        for (int i = 0; i < maxNumberOfTiles; i++){
            if((-widthX <= x) && (x <= widthX/2) && (-heightY <= y) && (y <= heightY/2)) {
                drawableMatrix[x + widthX /2][y + heightY/2] = currentTile;

                if (direction == 1) {
                    currentTile = currentTile.right;
                } else if (direction == 2) {
                    currentTile = currentTile.up;
                } else if (direction == 3) {
                    currentTile = currentTile.left;
                } else if (direction == 4) {
                    currentTile = currentTile.down;
                }

                System.out.println(currentTile.getPos());
                System.out.println(x+","+y);
            }

        if ((x == y) || ((x < 0) && (x == -y)) || ((x > 0) && (x == 1-y))) {
            longest=dx; dx=-dy; dy=longest;
            direction++;
            if (direction > 4) direction = 1;
        }
        x += dx; y += dy;
        }
        return drawableMatrix;
    }
}
