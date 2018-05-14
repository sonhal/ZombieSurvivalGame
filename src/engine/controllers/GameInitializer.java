package engine.controllers;

import engine.entities.components.EnemyInputComponent;
import engine.entities.components.interfaces.InputComponent;
import engine.entities.components.interfaces.WeaponComponent;
import engine.gamestate.GameStateKeeper;
import engine.gamestate.GameStateMessengerMediator;
import engine.entities.gameobjects.PlayerBuilder;
import engine.entities.components.PlayerInputComponent;
import engine.entities.components.SingleWeaponComponent;
import engine.entities.gameobjects.interfaces.IUpdatableGameObject;
import engine.entities.items.weapons.Gun;
import engine.services.pathfinder.PathSearchService;
import engine.world.Tile;
import engine.world.World;
import engine.services.ComponentService;
import engine.services.save.SaveGameHandler;
import view.GameViewController;

import java.io.IOException;
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

        PathSearchService pathSearchService = new PathSearchService(world);

        GameStateKeeper gameStateKeeper = new GameStateKeeper(gamerMediator);
        return new GameHandler(gameViewController,world,gameUpdater,
                eventHandler, player, gameStateKeeper, pathSearchService);
    }

    /**
     * Loads a saved game from memory if it exsists and returns a GameHandler with the loaded objects
     * @param gameViewController reference to the View rendering the game
     * @return GameHandler handling the big gameplay components and behaviour
     */
    public static GameHandler loadGameHandler(GameViewController gameViewController){
        GameHandler gameHandler = SaveGameHandler.loadGameHandler();
        gameHandler.getWorld().loadInGameWorld(gameHandler.getWorld().getWorld(), gameHandler.getPlayer());
        PathSearchService pathSearchService = new PathSearchService(gameHandler.getWorld());
        gameHandler.setGameViewController(gameViewController);
        gameHandler.setPathSearchService(pathSearchService);
        gameHandler.start();
        return gameHandler;
    }

    private static void setPlayerEventHandler(IUpdatableGameObject player, EventHandler eventHandler) {
         if(player.getComponentByType(InputComponent.class).isPresent()){
            PlayerInputComponent component = (PlayerInputComponent)
                    player.getComponentByType(InputComponent.class).get();
            component.setEventHandler(eventHandler);
        }
    }
}
