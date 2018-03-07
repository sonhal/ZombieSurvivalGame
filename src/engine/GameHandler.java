package engine;

import engine.composites.Sprite;
import view2D.GameViewController2D;

public class GameHandler{

    World world;
    GameViewController2D gameViewController2D;
    EventHandler eventHandler;
    Avatar player;

    public GameHandler(GameViewController2D gameViewController2D){
    this.gameViewController2D = gameViewController2D;
    this.player = AvatarFactory.create(new Sprite(1));

    createWorld(50);
    world.setPlayer(player);
    this.eventHandler = new EventHandler(player);
    }

    public void createWorld() {
        this.world = new World(10);
    }

    public void createWorld(int n) {
        this.world = new World(n);
    }

    public void updateWordState(){
        System.out.println("updating");
    }

    public DrawableMatrix getDrawableMatrix( int diameter){
        updateWordState();
        return new DrawableMatrix(world, world.getSeed(), diameter, diameter);
    }

    public void sendEvent(ActionEvent event){
        eventHandler.handle(event);

    }
}
