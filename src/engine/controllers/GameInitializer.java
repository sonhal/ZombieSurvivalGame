package engine.controllers;

import engine.controllers.gamestate.GameStateKeeper;
import engine.controllers.gamestate.GameStateMessengerMediator;
import engine.entities.PlayerBuilder;
import engine.entities.composites.AvatarInputComponent;
import engine.entities.composites.ComponentType;
import engine.entities.composites.WeaponComponent;
import engine.entities.interfaces.IUpdatableGameObject;
import engine.entities.items.weapons.Gun;
import engine.entities.world.Tile;
import engine.entities.world.World;
import engine.services.ComponentService;
import engine.services.save.SaveGameHandler;
import view.GameViewController;

import java.util.List;

/**
 * Entrypoint to get correctly initialized GameHandler and game Objects.
 */
public class GameInitializer {

    private static final int STANDARD_WORLD_SIZE = 50;

    /**
     * Creates a new game, returns a GameHandler with newly created objects
     * @param gameViewController
     * @return GameHandler handling the big gameplay components and behaviour
     */
    public static GameHandler newGame(GameViewController gameViewController){
        //Create World
        World world = new World();
        world.createNewGameWorld(STANDARD_WORLD_SIZE);
        //Game Updater
        GameUpdater gameUpdater = new GameUpdater();
        //Create EventHandler
        EventHandler eventHandler = new EventHandler();
        //Create Player object
        IUpdatableGameObject player = PlayerBuilder.create(gameUpdater, eventHandler,100, world.getSeed());
        world.setPlayer(player);
        setPlayerEventHandler(player,eventHandler);
        gameUpdater.addToUpdateList(player);
        GameStateMessengerMediator gamerMediator = new GameStateMessengerMediator();

        NpcController npcController = new NpcController(player, gamerMediator);
        GameStateKeeper gameStateKeeper = new GameStateKeeper();
        return new GameHandler(gameViewController,world,gameUpdater,
                eventHandler, player, npcController, gameStateKeeper);
    }

    /**
     * Loads a saved game from memory if it exsists and returns a GameHandler with the loaded objects
     * @param gameViewController reference to the View rendering the game
     * @return GameHandler handling the big gameplay components and behaviour
     */
    public static GameHandler loadGame(GameViewController gameViewController){
        World world = new World();
        List<Tile> worldTiles = SaveGameHandler.loadGame();
        System.out.println("loaded");
        IUpdatableGameObject player = SaveGameHandler.getPlayerInstance(worldTiles);

        System.out.println("added as event listener");
        world.loadInGameWorld(worldTiles, player);
        System.out.println("Game loaded");
        GameStateMessengerMediator gamerMediator = new GameStateMessengerMediator();
        NpcController npcController = new NpcController(player, gamerMediator);
        //Game Updater
        GameUpdater gameUpdater = new GameUpdater();
        //Create EventHandler
        EventHandler eventHandler = new EventHandler();
        setPlayerEventHandler(player, eventHandler);
        gameUpdater.addToUpdateList(player);
        for (IUpdatableGameObject enemy:
                SaveGameHandler.getEnemyInstances(worldTiles)) {
            npcController.addToUpdateList(enemy);
        }
        if(ComponentService.getComponentByType(player.getComponents(), ComponentType.WEAPON_COMPONENT).isPresent()){
            WeaponComponent weaponComponent = (WeaponComponent)
                    ComponentService.getComponentByType(player.getComponents(), ComponentType.WEAPON_COMPONENT).get();
            if(weaponComponent.getWeapon() instanceof Gun){
                Gun playerGun = (Gun)weaponComponent.getWeapon();
                playerGun.setController(gameUpdater);
            }
        }
        GameStateKeeper gameStateKeeper = new GameStateKeeper();
        return new GameHandler(gameViewController, world, gameUpdater,
                eventHandler, player, npcController, gameStateKeeper);
    }

    private static void setPlayerEventHandler(IUpdatableGameObject player, EventHandler eventHandler) {
        if(player.getComponentByType(ComponentType.INPUT_COMPONENT).isPresent()){
            AvatarInputComponent component = (AvatarInputComponent)
                    player.getComponentByType(ComponentType.INPUT_COMPONENT).get();
            component.setEventHandler(eventHandler);
        }
    }
}
