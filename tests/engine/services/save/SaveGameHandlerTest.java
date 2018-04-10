package engine.services.save;

import engine.entities.Avatar;
import engine.entities.GameObject;
import engine.entities.composites.Sprite;
import engine.entities.world.Tile;
import engine.entities.world.World;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SaveGameHandlerTest {

    @BeforeEach
    void setUp() {
        SaveGameHandler saveGameHandler = new SaveGameHandler();

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveGame(){
        World world = new World(10);
        SaveGameHandler.saveGame(world.getWorld());
    }

    @Test
    void SaveTile(){
        Tile tile = new Tile(0,0,new Sprite(1));
        SaveGameHandler.saveTile(tile);
    }

    @Test
    void SaveTileWithGameObject(){
        Tile tile = new Tile(0,0,new Sprite(1));
        tile.setGameObject(new GameObject(new Sprite(1)));
        SaveGameHandler.saveTile(tile);
    }

    @Test
    void SaveTileWithAvatar(){
        Tile tile = new Tile(0,0,new Sprite(1));
        //tile.setGameObject(new Avatar(new Sprite(1)));
        SaveGameHandler.saveTile(tile);
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
        World world = new World(10);
        world.getWorld().get(2).setGameObject(new GameObject());
        SaveGameHandler.saveGame(world.getWorld());
        List<Tile> loadedWorld = SaveGameHandler.loadGame();
        //GameObject result = loadedWorld.get(2).getGameObject();
        //assertNotNull(result, "GameObject should be set");
    }


}