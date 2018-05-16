package engine.world;

import engine.controllers.EventHandler;
import engine.controllers.Updater;
import engine.entities.gameobjects.PlayerBuilder;
import engine.entities.gameobjects.interfaces.IUpdatableGameObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class WorldTest {

    World testWorld;

    @BeforeEach
    void setUp() {
        testWorld = new World();
        testWorld.createNewGameWorld(20);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void generate() {
        long t1 = System.nanoTime();
        List<Tile> tileList = testWorld.generate(60);
        long t2 = System.nanoTime();
        if (tileList.size() != 14641) {
            System.out.println("The correct amount of tiles are not being generated. The correct number should equal the result of: ((initSize*2)(initSize*2))");
            assert (false);
        }
        if ((t2 - t1) > 100000000) {
            System.out.println(t2 - t1);
            System.out.println("Tile generation is taking to long. Curently taking:" + TimeUnit.NANOSECONDS.toMillis(t2 - t1) + "millisecunds");
            assert (false);
        }
        assert (true);
    }

    @Test
    void connectTiles() {
        //testWorld.setWorld(testWorld.generate(100));
        long t1 = System.nanoTime();
        testWorld.connectTiles(testWorld.getWorld());
        long t2 = System.nanoTime();
        System.out.println(TimeUnit.NANOSECONDS.toMillis(t2 - t1) + "millisecunds");
        System.out.println(t2 - t1);
        if (TimeUnit.NANOSECONDS.toMillis(t2 - t1) > 1000) {
            System.out.println("World is spending much time connecting tiles! Time spent: " + TimeUnit.NANOSECONDS.toMillis(t2 - t1) + "millisecunds.");
            assert (false);
        }
        if (testWorld.findTile(10, 10).getLeft().getCordX() != 9) {
            System.out.println("World tiles not mapped correctly");
            assert (false);
        }
        testWorld.getWorld().stream().forEach(tile -> {
            if (!(Integer.valueOf(tile.getRight().getCordX()).equals(Integer.valueOf(tile.getCordX() + 1)) || Integer.valueOf(tile.getRight().getCordX()).equals(Integer.valueOf(-tile.getCordX())-1))) {
                System.out.println("Right tile connection for cell x: " + tile.getCordX() + " y: " + tile.getCordY() + " is not connected properly");
                assert (false);
            }
            if (!(Integer.valueOf(tile.getLeft().getCordX()).equals(Integer.valueOf(tile.getCordX() - 1)) || Integer.valueOf(tile.getLeft().getCordX()).equals(Integer.valueOf(-tile.getCordX())-1))) {
                System.out.println("Left tile connection for cell x: " + tile.getCordX() + " y: " + tile.getCordY() + " is not connected properly");
                assert (false);
            }
            if (!(Integer.valueOf(tile.getUp().getCordY()).equals(Integer.valueOf(tile.getCordY() - 1)) || Integer.valueOf(tile.getUp().getCordY()).equals(Integer.valueOf(-tile.getCordY())-1))) {
                System.out.println("Up tile connection for cell x: " + tile.getCordX() + " y: " + tile.getCordY() + " is not connected properly");
                assert (false);
            }
            if (!(Integer.valueOf(tile.getDown().getCordY()).equals(Integer.valueOf(tile.getCordY() + 1)) || Integer.valueOf(tile.getDown().getCordY()).equals(Integer.valueOf(-tile.getCordY())-1))) {
                System.out.println("Down down");
                assert (false);
            }
        });

        assert (true);
    }

    @Test
    void findTile() {
        assertEquals((testWorld.findTile(5, 8).getCordX() == 5) && (testWorld.findTile(5, 8).getCordY() == 8), true);
    }

    @Test
    void getSeed() {
        System.out.println(testWorld.getSeed().getCordX());
        assertEquals(testWorld.getSeed().equals(testWorld.findTile(0, 0)), true);
    }

    @Test
    void setPlayer() {
        IUpdatableGameObject player = PlayerBuilder.create(new Updater(), new EventHandler(), 10, testWorld.getSeed());
        testWorld.setPlayer(player);
        assertEquals(testWorld.getPlayer(), player);
    }


    @Test
    void getWorld() {
        assertNotNull(testWorld.getWorld());
    }

    @Test
    void loadInGameWorld() {
        World world = new World();
        world.setWorld(new ArrayList<Tile>(5));
        world.setPlayer(PlayerBuilder.create(new Updater(), new EventHandler(), 10, testWorld.getSeed()));
        world.loadInGameWorld(testWorld.getWorld(), PlayerBuilder.create(new Updater(), new EventHandler(), 10, testWorld.getSeed()));
        assert (world.getSeed() != null);
    }
}