package engine;

public class GameHandler implements GameHandlerInterface{

    World world;


    @Override
    public void createWorld() {
        this.world = new World(10);
    }
    public void createWorld(int n) {
        this.world = new World(n);
    }

    DrawableMatrix getDrawableMatrix(Tile baseTile, int diameter){
        return new DrawableMatrix(world, baseTile, diameter);
    }
}
