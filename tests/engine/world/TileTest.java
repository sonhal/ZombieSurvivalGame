package engine.world;

import engine.controllers.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    World testWorld;

    @BeforeEach
    void setUp() {
        testWorld = new World();
        testWorld.createNewGameWorld(20);
    }

    @Test
    void getTileInDirection() {
        assertEquals(testWorld.getSeed().getTileInDirection(Direction.RIGHT), testWorld.getSeed().getRight());
    }
}