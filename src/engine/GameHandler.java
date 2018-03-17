package engine;

import engine.composites.Sprite;
import view2D.GameViewController2D;

public class GameHandler{

    World world;
    GameViewController2D gameViewController2D;
    EventHandler eventHandler;
    Avatar player;
    DrawableMatrix matrix;


    public GameHandler(GameViewController2D gameViewController2D){
    this.gameViewController2D = gameViewController2D;
    this.player = AvatarFactory.create(new Sprite(2));

    createWorld(50);
    world.setPlayer(player);
    this.eventHandler = new EventHandler(player);
    this.matrix = getDrawableMatrix(10, 10);
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

    public DrawableMatrix getDrawableMatrix( int diameterX, int diameterY){
        updateWordState();
        return new DrawableMatrix(world, world.getSeed(), diameterX, diameterY);
    }

    public DrawableTile[][] getDrableWorld(){
        return matrix.generateDrawable(world,player.getTransformComponent().getCurrentTile(),10,10);
    }

    public void sendEvent(ActionEvent event){
        eventHandler.handle(event);
    }

    public void setObjectInWorld(){
        int x = player.getTransformComponent().getCurrentTile().cordX;
        int y = player.getTransformComponent().getCurrentTile().cordY;
        Tile tile = world.findTile(x + 3,y + 3);
        tile.setGameObject(GameObjectFactory.create(new Sprite(3)));
    }
}
