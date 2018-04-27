package engine.controllers;

import engine.controllers.gamestate.GameStateKeeper;
import engine.controllers.gamestate.GameStateMessengerMediator;
import engine.entities.PlayerBuilder;
import engine.entities.composites.AvatarInputComponent;
import engine.entities.composites.ComponentType;
import engine.entities.composites.TransformComponent;
import engine.entities.composites.WeaponComponent;
import engine.entities.interfaces.IUpdatableGameObject;
import engine.entities.items.weapons.Gun;
import engine.entities.world.Tile;
import engine.entities.world.World;
import engine.services.ComponentService;
import engine.services.audio.AudioPlayer;
import engine.services.audio.Sound;
import engine.services.save.SaveGameHandler;
import engine.view.DrawableMatrix;
import engine.view.DrawableTile;
import view.GameViewController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class controlling the state and changes in the game world.
 */
public class GameHandler extends Updater {

    private World world;
    private GameViewController gameViewController;
    private NpcController npcController;
    private EventHandler eventHandler;
    private IUpdatableGameObject player;
    private DrawableMatrix matrix;
    private GameStateKeeper gameStateKeeper;
    private GameUpdater gameUpdater;
    private GameStateMessengerMediator gameStateMessenger;


    public GameHandler(GameViewController gameViewController2D, World gameWorld, GameUpdater gameUpdater,
                       EventHandler eventHandler, IUpdatableGameObject player, NpcController npcController,
                       GameStateKeeper gameStateKeeper){
        //Set gameplay components
        System.out.println("GameHandler active");
        this.gameViewController = gameViewController2D;
        this.eventHandler = eventHandler;
        this.world = gameWorld;
        this.gameUpdater = gameUpdater;
        this.player = player;
        this.gameStateMessenger = new GameStateMessengerMediator();
        this.npcController  = npcController;


        //Create the DrawableMatrix that handles the cut of the game world passed to the view
        this.matrix = getDrawableMatrix(10);
        //Create GameStateKeeper
        this.gameStateKeeper = gameStateKeeper;
        //Set background music
        ArrayList<Sound> backGroundMusic = new ArrayList<Sound>();
        backGroundMusic.add(Sound.BACKGROUND_MUSIC_1);
        AudioPlayer.getInstance().setBackgroundMusic(backGroundMusic);
    }

    /**
     * Main update method for the game state.
     */
    public void updateWordState(){
        npcController.update(world);
        gameUpdater.update();
        AudioPlayer.getInstance().playSounds();
        if(player.isDead()){
            handlePlayerDeath();}
    }

    /**
     * Method called by the view to get updated game state.
     * @return DrawableTile 2D array
     */
    public DrawableTile[][] getDrawableWorld(){
        Tile viewCenterTile;
        updateWordState();

        //Ugly edge case handling for first couple of updates
        if(player.getComponentByType(ComponentType.TRANSFORM_COMPONENT).isPresent()){
            TransformComponent playerTransformComponent = (TransformComponent)
                    player.getComponentByType(ComponentType.TRANSFORM_COMPONENT).get();
            if(playerTransformComponent.getCurrentTile() == null){
                viewCenterTile = world.findTile(0,0);
            }
            else {
                viewCenterTile = playerTransformComponent.getCurrentTile();
            }
            return matrix.generateDrawable(world, viewCenterTile,15,15);
        }
        else {
            throw  new RuntimeException("Player is not in World");
        }
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
            shutDown();
            System.out.println("Game ended");
            gameViewController.goToDeathScreen();
        }catch (IOException err){
            System.out.println("Could not end game");
            err.printStackTrace();
        }
    }

    public IUpdatableGameObject getPlayer() {
        return player;
    }

    public World getWorld() {
        return world;
    }

    public void saveGame(){
        shutDown();
        gameViewController = null;
        SaveGameHandler.saveGame(world.getWorld());
        System.out.println("Game saved");
    }

    public void shutDown(){
        AudioPlayer.getInstance().stopBackgroundMusic();
        AudioPlayer.getInstance().shutdown();
    }
}
