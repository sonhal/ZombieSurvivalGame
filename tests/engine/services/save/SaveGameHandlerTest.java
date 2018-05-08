package engine.services.save;

import engine.controllers.EventHandler;
import engine.controllers.Updater;
import engine.entities.gameobjects.PlayerBuilder;
import engine.entities.components.ComponentType;
import engine.entities.gameobjects.Sprite;
import engine.entities.components.TransformComponent;
import engine.entities.gameobjects.interfaces.IUpdatableGameObject;
import engine.world.Tile;
import engine.world.World;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SaveGameHandlerTest {

    SaveGameHandler saveGameHandler;
    World gameWorld;

    @BeforeEach
    void setUp() {
        this.saveGameHandler = new SaveGameHandler();
        this.gameWorld = new World();
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void SaveTileList(){
        Tile tile = new Tile(0,0,new Sprite(1));
        List<Tile> tileList = new ArrayList<>();
        tileList.add(tile);
        tileList.add(new Tile(1,1,new Sprite(1)));
        SaveGameHandler.saveGame(tileList);
    }

    @Test
    void loadGameTest(){
        gameWorld.createNewGameWorld(10);
        Tile testTile = gameWorld.getWorld().get(2);
        IUpdatableGameObject player = PlayerBuilder.create(new Updater(), new EventHandler(),10, testTile);
        TransformComponent tc = (TransformComponent) player.getComponentByType(ComponentType.TRANSFORM_COMPONENT).get();
        SaveGameHandler.saveGame(gameWorld.getWorld());
        List<Tile> loadedWorld = SaveGameHandler.loadGame();
        IUpdatableGameObject result = (IUpdatableGameObject) loadedWorld.get(2).getGameObject();
        assertNotNull(result, "GameObject should be set");
        Tile resultTile = result.getTile();
        assertEquals(testTile.getCordX(), resultTile.getCordX(), "Tiles not the same");
        assertEquals(testTile.getCordY(), resultTile.getCordY(), "Tiles not the same");
    }

    @Test
    void saveWorldTest(){
        gameWorld.createNewGameWorld(10);
        List<Tile> tiles = gameWorld.getWorld();
        SaveGameHandler.saveGame(tiles);
    }




}