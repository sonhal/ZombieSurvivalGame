package engine.controllers;

import engine.entities.Player;
import engine.entities.world.World;
import engine.view.DrawableMatrix;
import engine.view.DrawableTile;
import view2D.GameViewController2D;

import java.io.IOException;

/**
 * Class controlling the state and changes in the game world.
 */
public class GameHandler extends ScriptableObjectUpdater {

    private World world;
    private GameViewController2D gameViewController2D;
    private NpcController npcController;
    private EventHandler eventHandler;
    private Player player;
    private DrawableMatrix matrix;


    public GameHandler(GameViewController2D gameViewController2D){
        System.out.println("GameHandler active");
        this.gameViewController2D = gameViewController2D;
        innitGame();

    }

    private void innitGame(){
        //Create Player object
        this.player = new Player(this);
        //Create EventHandler that controls the flow of events for the view
        this.eventHandler = new EventHandler();
        //Add the player to listeners in the eventListener
        this.eventHandler.setNewListener(player);
        //Add player to list of objects that the EventHandler updates each tick
        this.addToUpdateList(player);
        //Create game world
        createWorld(50);
        //Set player in world
        world.setPlayer(player.getAvatar());
        this.npcController  = new NpcController(this, player.getAvatar());
        //Create the DrawableMatrix that handles the cut of the game world passed to the view
        this.matrix = getDrawableMatrix(10);
    }

    /**
     * Creates a new instance of World.
     */
    public void createWorld() {
        this.world = new World(10);
    }

    /**
     * Creates a new instance of World with a given size.
     * @param size declares the size of the world to be created.
     */
    public void createWorld(int size) {
        this.world = new World(size);
    }

    /**
     * Main update method for the game state.
     */
    public void updateWordState(){
        if(player.isDead()){
            handlePlayerDeath();}
        npcController.update(1, world);
        updateScriptableObjects();
    }

    /**
     * Method called by the view to get updated game state.
     * @return DrawableTile 2D array
     */
    public DrawableTile[][] getDrawableWorld(){
        updateWordState();
        return matrix.generateDrawable(world,player.getAvatar().getTransformComponent().getCurrentTile(),10,10);
    }

    private DrawableMatrix getDrawableMatrix( int diameter){
        return new DrawableMatrix(world, world.getSeed(), diameter, diameter);
    }

    /**
     * Method in taking events form the view and passes it to the eventHandler registered in the GameHandler
     * @param event ActionEvent
     */
    public void sendEvent(ActionEvent event){
        eventHandler.handle(event);
    }


    /**
     * Method called when player dies from the Player object
     */
    public void handlePlayerDeath(){
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
