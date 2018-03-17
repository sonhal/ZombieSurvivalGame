package engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view2D.GameViewController2D;

import static org.junit.jupiter.api.Assertions.*;

class GameHandlerTest {

    private GameHandler gameHandler;
    private GameViewController2D gameViewController2D;

    @BeforeEach
    void setUp() {
        gameHandler = new GameHandler(new GameViewController2D());
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