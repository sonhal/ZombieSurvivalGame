package engine.controllers;

import engine.entities.Avatar;
import engine.entities.PlayerFactory;
import engine.entities.composites.AvatarInputComponent;
import engine.entities.items.weapons.Gun;
import engine.entities.world.Tile;
import engine.entities.world.World;
import engine.services.save.SaveGameHandler;
import engine.view.DrawableMatrix;
import engine.view.DrawableTile;
import view.GameViewController;
import view2D.GameViewController2D;

import java.io.IOException;
import java.util.List;

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
        //Create EventHandler that controls the flow of events from the view
        this.eventHandler = new EventHandler();
        this.world = new World();
    }

    public void startNewGame(){
        //Create Player object
        this.player = PlayerFactory.create(this, eventHandler,100);
        //Create game world
        createWorld(50);
        //Set player in world
        world.setPlayer(player);
        innitGame();
    }

    public void loadGame(){
        List<Tile> worldTiles = SaveGameHandler.loadGame();
        System.out.println("loaded");
        setPlayer(SaveGameHandler.getPlayerInstance(worldTiles));
        System.out.println("added as eventlistner");
        world.loadInGameWorld(worldTiles, player);
        System.out.println("Game loaded");
        innitGame();
        for (Avatar enemy:
             SaveGameHandler.getEnemyInstances(worldTiles)) {
            npcController.addToUpdateList(enemy);
        }
        if(player.getWeaponComponent().getWeapon() instanceof Gun){
            Gun playerGun = (Gun) player.getWeaponComponent().getWeapon();
            playerGun.setController(this);
        }
    }

    private void innitGame(){
        //Object responsible for enemies in the game world
        this.npcController  = new NpcController(this, player);
        //Add player to list of objects that the EventHandler updates each tick
        this.addToUpdateList(player);
        //Create the DrawableMatrix that handles the cut of the game world passed to the view
        this.matrix = getDrawableMatrix(10);
    }

    /**
     * Creates a new instance of World.
     */
    public void createWorld() {
        this.world.createNewGameWorld(10);
    }

    /**
     * Creates a new instance of World with a given size.
     * @param size declares the size of the world to be created.
     */
    public void createWorld(int size) {
        this.world.createNewGameWorld(size);
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

    public void setPlayer(Avatar player) {
        AvatarInputComponent component = (AvatarInputComponent)player.getInputComponent();
        component.setEventHandler(eventHandler);
        this.player = player;
    }

    public World getWorld() {
        return world;
    }

    public void saveGame(){
        gameViewController = null;
        SaveGameHandler.saveGame(world.getWorld());
        System.out.println("Game saved");
    }
}
