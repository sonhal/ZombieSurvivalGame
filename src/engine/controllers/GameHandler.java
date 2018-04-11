package engine.controllers;

import engine.entities.Avatar;
import engine.entities.PlayerFactory;
import engine.entities.world.World;
import engine.services.save.SaveGameHandler;
import engine.view.DrawableMatrix;
import engine.view.DrawableTile;
import view.GameViewController;
import view2D.GameViewController2D;

import java.io.IOException;

/**
 * Class controlling the state and changes in the game world.
 */
public class GameHandler extends Updater {

    private World world;
    private GameViewController gameViewController;
    private NpcController npcController;
    private EventHandler eventHandler;
    private Avatar player;
    private DrawableMatrix matrix;


    public GameHandler(GameViewController gameViewController2D){
        System.out.println("GameHandler active");
        this.gameViewController = gameViewController2D;
        innitGame();
    }

    private void innitGame(){
        //Create EventHandler that controls the flow of events from the view
        this.eventHandler = new EventHandler();
        //Create Player object
        this.player = PlayerFactory.create(this, eventHandler,100);
        //Add player to list of objects that the EventHandler updates each tick
        this.addToUpdateList(player);
        //Create game world
        createWorld(50);
        //Set player in world
        world.setPlayer(player);
        this.npcController  = new NpcController(this, player);
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
        update();
    }

    /**
     * Method called by the view to get updated game state.
     * @return DrawableTile 2D array
     */
    public DrawableTile[][] getDrawableWorld(){
        updateWordState();
        return matrix.generateDrawable(world,player.getTransformComponent().getCurrentTile(),10,10);
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
            gameViewController.goToMenu();
        }catch (IOException err){
            System.out.println("Could not end game");
            err.printStackTrace();
        }
    }

    public Avatar getPlayer() {
        return player;
    }

    public World getWorld() {
        return world;
    }

    public void saveGame(){
        gameViewController.stopGameLoop();
        gameViewController = null;
        SaveGameHandler.saveGame(world.getWorld());
        System.out.println("Game saved");
    }

    public void loadGame(){
        world.setWorld(SaveGameHandler.loadGame());
        System.out.println("Game loaded");

    }
}
