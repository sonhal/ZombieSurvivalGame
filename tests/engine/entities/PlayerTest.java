package engine.entities;

import engine.controllers.ActionEvent;
import engine.controllers.GameHandler;
import engine.entities.composites.GraphicsComponent;
import engine.entities.composites.Sprite;
import engine.entities.composites.TransformComponent;
import engine.entities.world.Tile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view2D.GameViewController2D;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player player;
    GameHandler gameHandler;

    @BeforeEach
    void setUp() {
        gameHandler = new GameHandler(new GameViewController2D());
        player = gameHandler.getPlayer();
    }

    @AfterEach
    void tearDown() {
    }
    @Test
    void start() {
    }

    @Test
    void stop() {
    }

    @Test
    void update() {
    }

    @Test
    void handleEvent() {
        //Should atm fail, we have some logic issues in the world data structure
        Tile toTile = player.getAvatar().getTile().getUp();
        player.handleEvent(ActionEvent.MOVE_UP);
        assertEquals(player.getAvatar().getTile(), toTile);
    }

    @Test
    void getAvatar() {
        assertTrue(player.getAvatar() != null);
    }

}