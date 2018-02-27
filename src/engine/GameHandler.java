package engine;

import view2D.GameViewController2D;

public class GameHandler{

    World world;
    GameViewController2D gameViewController2D;

public GameHandler(GameViewController2D gameViewController2D){
    this.gameViewController2D = gameViewController2D;
}
    public void createWorld() {
        this.world = new World(10);
    }
    public void createWorld(int n) {
        this.world = new World(n);
    }

    DrawableMatrix getDrawableMatrix(Tile baseTile, int diameter){
        return new DrawableMatrix(world, baseTile, diameter);
    }
    public void sendEvent(ActionEvent event){

    }
}
