package engine.controllers;

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
    private AudioPlayer audioPlayer;


    public GameHandler(GameViewController gameViewController2D){
        System.out.println("GameHandler active");
        this.gameViewController = gameViewController2D;
        //Create EventHandler that controls the flow of events from the view
        this.eventHandler = new EventHandler();
        this.world = new World();

    }

    public void startNewGame(){

        //Create game world
        createWorld(50);
        //Create Player object
        this.player = PlayerBuilder.create(this, eventHandler,100, world.getSeed());
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
        for (IUpdatableGameObject enemy:
             SaveGameHandler.getEnemyInstances(worldTiles)) {
            npcController.addToUpdateList(enemy);
        }
        if(ComponentService.getComponentByType(player.getComponents(), ComponentType.WEAPON_COMPONENT).isPresent()){
            WeaponComponent weaponComponent = (WeaponComponent)
                    ComponentService.getComponentByType(player.getComponents(), ComponentType.WEAPON_COMPONENT).get();
            if(weaponComponent.getWeapon() instanceof Gun){
                Gun playerGun = (Gun)weaponComponent.getWeapon();
                playerGun.setController(this);
            }
        }
    }

    private void innitGame(){
        //Object responsible for enemies in the game world
        this.npcController  = new NpcController(this, player);
        //Add player to list of objects that the EventHandler updates each tick
        this.addToUpdateList(player);
        //Create the DrawableMatrix that handles the cut of the game world passed to the view
        this.matrix = getDrawableMatrix(10);

        this.audioPlayer = AudioPlayer.getInstance();
        ArrayList<Sound> backGroundMusic = new ArrayList<Sound>();
        backGroundMusic.add(Sound.BACKGROUND_MUSIC_1);
        this.audioPlayer.setBackgroundMusic(backGroundMusic);

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
        npcController.update(1, world);
        update();
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

    public void setPlayer(IUpdatableGameObject player) {
        if(player.getComponentByType(ComponentType.INPUT_COMPONENT).isPresent()){
            AvatarInputComponent component = (AvatarInputComponent)
                    player.getComponentByType(ComponentType.INPUT_COMPONENT).get();
            component.setEventHandler(eventHandler);
            this.player = player;
        }
    }

    public World getWorld() {
        return world;
    }

    public void saveGame(){
        shutDown();
        gameViewController = null;
        audioPlayer = null;
        SaveGameHandler.saveGame(world.getWorld());
        System.out.println("Game saved");
    }

    public void shutDown(){
        audioPlayer.stopBackgroundMusic();
        audioPlayer.shutdown();
    }
}
