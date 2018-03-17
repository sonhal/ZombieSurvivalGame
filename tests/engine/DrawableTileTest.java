package engine;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DrawableTileTest {

    DrawableTile testTile;

    @BeforeEach
    void setUp() {
         testTile = new DrawableTile();
    }

    @AfterEach
    void tearDown() {

    }
    @Test
    void setGameAndItem() {
        testTile.setGameAndItem(null, new Weapon(new Sprite(2), 10,10));
            assertNull(testTile.getGameObject());
            assertNotNull(testTile.getItem());
    }

    @Test
    void getItem(){
        testTile.setItem(new Weapon(100));
        assertNotNull(testTile.getItem());
    }
}