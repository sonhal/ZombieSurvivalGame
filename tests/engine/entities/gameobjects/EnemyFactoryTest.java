package engine.entities.gameobjects;

import engine.controllers.ActionEvent;
import engine.controllers.GameHandler;
import engine.controllers.GameUpdater;
import engine.controllers.interfaces.IGameHandler;
import engine.entities.components.StaticTransformComponent;
import engine.entities.gameobjects.interfaces.IUpdatableGameObject;
import engine.gamestate.GameStateKeeper;
import engine.services.audio.Sound;
import engine.services.pathfinder.PathSearchService;
import engine.view.DrawableMatrix;
import engine.view.DrawableTile;
import engine.world.Tile;
import engine.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.GameViewController;

import static org.junit.jupiter.api.Assertions.*;

class EnemyFactoryTest {

    StaticGameObject gameObject;
    Tile testTile;
    StaticTransformComponent testTransformComponent;
    IGameHandler gameHandler;

    @BeforeEach
    void setUp() {
        testTile = new Tile(1,1, new Sprite(1));
        gameHandler = new IGameHandler(){

            @Override
            public void updateWordState() {

            }

            @Override
            public DrawableTile[][] getDrawableWorld() {
                return new DrawableTile[0][];
            }

            @Override
            public DrawableMatrix getDrawableMatrix(int diameter) {
                return null;
            }

            @Override
            public void sendEvent(ActionEvent event) {

            }

            @Override
            public void handlePlayerDeath() {

            }

            @Override
            public IUpdatableGameObject getPlayer() {
                return null;
            }

            @Override
            public World getWorld() {
                return null;
            }

            @Override
            public void saveGame() {

            }

            @Override
            public void shutDown() {

            }

            @Override
            public void start() {

            }

            @Override
            public GameStateKeeper getGameStateKeeper() {
                return null;
            }

            @Override
            public GameUpdater getGameUpdater() {
                return null;
            }

            @Override
            public void setGameViewController(GameViewController gameViewController) {

            }

            @Override
            public void setPathSearchService(PathSearchService pathSearchService) {

            }

            @Override
            public PathSearchService getPathSearchService() {
                return null;
            }
        };
    }

    @Test
    void createEnemy() {
        IUpdatableGameObject gameObject = EnemyFactory.createEnemy(EnemyFactory.EnemyType.ZOMBIE, gameHandler, testTile, new BasicEntityBlueprint(1,1,1,Sound.ZOMBIE_ATTACK));
        assertNotNull(gameObject);
    }
}