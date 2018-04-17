package engine.entities.world;

import engine.entities.Avatar;
import engine.entities.PlayerBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class WorldTest {

    World testWorld;

    @BeforeEach
    void setUp() {
        testWorld = new World(60);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void generate() {
        long t1 = System.nanoTime();
        List<Tile> tileList = testWorld.generate(60);
        long t2 = System.nanoTime();
        if(tileList.size() != 14400){
            System.out.println("The correct amount of tiles are not being generated. The correct number should equal the result of: ((initSize*2)(initSize*2))");
            assert(false);
        }
        if((t2-t1) > 100000000 ){
            System.out.println(t2-t1);
            System.out.println("Tile generation is taking to long. Curently taking:" + TimeUnit.NANOSECONDS.toMillis(t2-t1) + "millisecunds");
            assert(false);
        }
        assert(true);
    }

    @Test
    void connectTiles() {
        testWorld.setWorld(testWorld.generate(100));
        long t1 = System.nanoTime();
        testWorld.connectTiles(testWorld.getWorld());
        long t2 = System.nanoTime();
        System.out.println(TimeUnit.NANOSECONDS.toMillis(t2-t1) + "millisecunds");
        System.out.println(t2-t1);
        if (TimeUnit.NANOSECONDS.toMillis(t2-t1) > 1000){
            System.out.println("World is spending much time connecting tiles! Time spent: " + TimeUnit.NANOSECONDS.toMillis(t2-t1) + "millisecunds.");
            assert(false);
        }
        if (testWorld.findTile(10,10).getLeft().getCordX() != 9){
            System.out.println("World tiles not mapped correctly");
            assert(false);
        }
        assert(true);
    }

    @Test
    void findTile() {
        assertEquals((testWorld.findTile(5,8).getCordX() == 5) && (testWorld.findTile(5,8).getCordY() == 8), true);
    }

    @Test
    void getSeed() {
        assertEquals(testWorld.getSeed().equals(testWorld.findTile(0,0)), true);
    }

    @Test
    void setPlayer() {

    }

    @Test
    void getPlayer() {

    }

    @Test
    void getWorld() {
        assertNotNull(testWorld.getWorld());
    }
}