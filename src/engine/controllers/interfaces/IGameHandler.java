package engine.controllers.interfaces;

import engine.controllers.ActionEvent;
import engine.controllers.GameUpdater;
import engine.entities.gameobjects.interfaces.IUpdatableGameObject;
import engine.gamestate.GameStateKeeper;
import engine.services.pathfinder.PathSearchService;
import engine.view.DrawableMatrix;
import engine.view.DrawableTile;
import engine.world.World;
import view.GameViewController;

public interface IGameHandler {
    void updateWordState();

    DrawableTile[][] getDrawableWorld();

    DrawableMatrix getDrawableMatrix(int diameter);

    void sendEvent(ActionEvent event);

    void handlePlayerDeath();

    IUpdatableGameObject getPlayer();

    World getWorld();

    void saveGame();

    void shutDown();

    void start();

    GameStateKeeper getGameStateKeeper();

    GameUpdater getGameUpdater();

    void setGameViewController(GameViewController gameViewController);

    void setPathSearchService(PathSearchService pathSearchService);

    PathSearchService getPathSearchService();
}
