package engine.controllers;

import engine.entities.Enemy;
import engine.entities.GameObject;
import engine.entities.Player;
import engine.entities.composites.Sprite;
import engine.entities.world.Tile;
import engine.entities.world.World;
import engine.view.DrawableMatrix;
import engine.view.DrawableTile;
import view2D.GameViewController2D;

import java.io.IOException;

public class GameHandler extends ScriptableObjectController {

    private World world;
    private GameViewController2D gameViewController2D;
    private NpcController npcController;
    private EventHandler eventHandler;
    private Player player;
    private DrawableMatrix matrix;


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
        int x = player.getAvatar().getTransformComponent().getCurrentTile().getCordX();
        int y = player.getAvatar().getTransformComponent().getCurrentTile().getCordY();
        Tile tile = world.findTile(x + 3,y + 3);
        tile.setGameObject(new GameObject(new Sprite(3)));
    }

    public void playerDied(){
        try {
            System.out.println("Game ended");
            gameViewController2D.goToMenu();
        }catch (IOException err){
            System.out.println("Could not end game");
            err.printStackTrace();
        }
    }

    public Player getPlayer() {
        return player;
    }
}
