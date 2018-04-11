package engine.services.save;

import engine.GameViewStub;
import engine.controllers.EventHandler;
import engine.controllers.GameHandler;
import engine.controllers.Updater;
import engine.entities.Avatar;
import engine.entities.GameObject;
import engine.entities.PlayerFactory;
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
        World world = new World();
        SaveGameHandler.saveGame(world.getWorld());
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
        World world = new World();
        Tile testTile = world.getWorld().get(2);
        Avatar player = PlayerFactory.create(new Updater(), new EventHandler(),10);
        player.getTransformComponent().setCurrentTile(testTile);
        SaveGameHandler.saveGame(world.getWorld());
        List<Tile> loadedWorld = SaveGameHandler.loadGame();
        Avatar result = (Avatar) loadedWorld.get(2).getGameObject();
        assertNotNull(result, "GameObject should be set");
        Tile resultTile = result.getTile();
        assertEquals(testTile.getCordX(), resultTile.getCordX(), "Tiles not the same");
        assertEquals(testTile.getCordY(), resultTile.getCordY(), "Tiles not the same");
    }

    @Test
    void saveWorldTest(){
        GameViewStub gameViewStub = new GameViewStub();
        GameHandler gameHandler = new GameHandler(gameViewStub);
        World world = gameHandler.getWorld();
        List<Tile> tiles = world.getWorld();


        SaveGameHandler.saveGame(tiles);
        System.out.println("Game saved");
        //List<Tile> loadedWorld = SaveGameHandler.loadGame();
        //assertNotNull(loadedWorld);
    }




}