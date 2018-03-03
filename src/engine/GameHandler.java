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

    public DrawableMatrix getDrawableMatrix( int diameter){
        createWorld(50);
        return new DrawableMatrix(world, world.seed, diameter);
    }

    public void sendEvent(ActionEvent event){

    }
}
