package engine.services.save;

import engine.controllers.EventHandler;
import engine.controllers.Updater;
import engine.entities.components.StaticTransformComponent;
import engine.entities.components.interfaces.TransformComponent;
import engine.entities.gameobjects.PlayerBuilder;
import engine.entities.gameobjects.Sprite;
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
    }

    @Test
    void loadGameTest(){
        gameWorld.createNewGameWorld(10);
        Tile testTile = gameWorld.getWorld().get(2);
        IUpdatableGameObject player = PlayerBuilder.create(new Updater(), new EventHandler(),10, testTile);
        StaticTransformComponent tc = (StaticTransformComponent) player.getComponentByType(TransformComponent.class).get();

    }

    @Test
    void saveWorldTest(){
        gameWorld.createNewGameWorld(10);
        List<Tile> tiles = gameWorld.getWorld();

    }




}