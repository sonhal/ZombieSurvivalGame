package engine;

import engine.composites.Sprite;
import view2D.GameViewController2D;

import java.io.IOException;

public class GameHandler extends ScriptableObjectController{

    World world;
    GameViewController2D gameViewController2D;
    NpcController npcController;
    EventHandler eventHandler;
    Player player;
    DrawableMatrix matrix;


    public GameHandler(GameViewController2D gameViewController2D){
        System.out.println("GameHandler active");
    this.gameViewController2D = gameViewController2D;
    this.eventHandler = new EventHandler();

    this.player = new Player(this);
    this.eventHandler.setNewListener(player);
    this.addToUpdateList(player);

    createWorld(50);
    world.setPlayer(player.getAvatar());

    this.npcController  = new NpcController(this, player.getAvatar());
    Enemy enemy = new Enemy(npcController, this, player.getAvatar());

    this.matrix = getDrawableMatrix(10);
    }

    public void createWorld() {
        this.world = new World(10);
    }

    public void createWorld(int n) {
        this.world = new World(n);
    }

    public void updateWordState(){
        npcController.update(1, world);
        updateScriptableObjects();
    }

    public DrawableMatrix getDrawableMatrix( int diameter){
        return new DrawableMatrix(world, world.getSeed(), diameter, diameter);
    }

    public DrawableTile[][] getDrawableWorld(){
        updateWordState();
        return matrix.generateDrawable(world,player.getAvatar().getTransformComponent().getCurrentTile(),10,10);
    }

    public void sendEvent(ActionEvent event){
        eventHandler.handle(event);
    }

    public void setObjectInWorld(){
        int x = player.getAvatar().getTransformComponent().getCurrentTile().cordX;
        int y = player.getAvatar().getTransformComponent().getCurrentTile().cordY;
        Tile tile = world.findTile(x + 3,y + 3);
        tile.setGameObject(GameObjectFactory.create(new Sprite(3)));
    }

    public void playerDied(){
        try {
            gameViewController2D.goToMenu();
        }catch (IOException err){
            System.out.println("Could not end game");
            err.printStackTrace();
        }
    }



}
