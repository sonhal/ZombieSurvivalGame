package engine;

import view2D.GameViewController2D;

public class GameHandler{

    World world;
    GameViewController2D gameViewController2D;
    EventHandler eventHandler;
    Avatar player;

    public GameHandler(GameViewController2D gameViewController2D){
    this.gameViewController2D = gameViewController2D;


    createWorld(50);
    this.eventHandler = null;
    }

    public void createWorld() {
        this.world = new World(10);
    }

    public void createWorld(int n) {
        this.world = new World(n);
    }

    public DrawableMatrix getDrawableMatrix( int diameter){
        return new DrawableMatrix(world, world.getSeed(), diameter, diameter);
    }

    public void sendEvent(ActionEvent event){
        eventHandler.handle(event);

    }
}
