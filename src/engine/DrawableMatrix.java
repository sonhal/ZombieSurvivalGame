package engine;

public class DrawableMatrix {
    public Tile[][] drawableMatrix;
    Tile currentTile;
    int direction = 1;

    DrawableMatrix(World world, Tile baseTile, int diameter) {
        drawableMatrix = generateDrawable(world, baseTile, diameter);
    }

    public Tile[][] generateDrawable(World world, Tile baseTile, int diameterDistance) {
        int size = diameterDistance * 2;
        drawableMatrix = new Tile[diameterDistance * 2 +10][diameterDistance * 2 + 10];
        this.currentTile = baseTile;
        int x, y, dx, dy;
        x = y = dx = 0;
        dy = -1;
        int t = diameterDistance * diameterDistance;
        int maxI = t * t;
        for (int i = 0; i < maxI; i++) {

            if ((-size / 2 <= x) && (x <= size / 2) && (-size / 2 <= y) && (y <= size / 2)) {
                drawableMatrix[x + diameterDistance][y + diameterDistance] = currentTile;

                if (direction == 0) {
                    this.currentTile = currentTile.right;
                } else if (direction == 1) {
                    this.currentTile = currentTile.up;
                } else if (direction == 2) {
                    this.currentTile = currentTile.left;
                } else if (direction == 3) {
                    this.currentTile = currentTile.down;
                }

                System.out.println(currentTile.getPos());

            }
            if ((x == y) || ((x < 0) && (x == -y)) || ((x > 0) && (x == 1 - y))) {
                t = dx;
                dx = -dy;
                dy = t;
                direction++;
                if (direction > 3) direction = 0;
            }
            x += dx;
            y += dy;
        }
        return drawableMatrix;
    }

    DrawableTile convertTile(Tile t) {
        return new DrawableTile(t.getGameObject(), t.getItem());
    }

}
