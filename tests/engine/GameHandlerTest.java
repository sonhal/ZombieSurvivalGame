package engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameHandlerTest {

    private GameHandler gameHandler;

    @BeforeEach
    void setUp() {
        gameHandler = new GameHandler();
    }

    @Test
    void createWorld() {
        gameHandler.createWorld(50);
        assertEquals("x: 2 y: 3", gameHandler.world.findTile(2,3).getPos());
    }

    @Test
    void getDrawableMatrix() {
    }

}