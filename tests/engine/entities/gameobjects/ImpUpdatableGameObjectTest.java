package engine.entities.gameobjects;

import engine.entities.components.ComponentType;
import engine.entities.components.ScriptableComponent;
import engine.entities.components.TransformComponent;
import engine.world.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ImpUpdatableGameObjectTest {

    ImpUpdatableGameObject gameObject;
    Tile testTile;
    TransformComponent testTransformComponent;

    @BeforeEach
    void setUp() {
        testTile = new Tile(1,1, new Sprite(1));
        testTransformComponent = new TransformComponent(testTile);
        ArrayList<ScriptableComponent> list = new ArrayList<>();
        list.add(testTransformComponent);
        gameObject = new ImpUpdatableGameObject(list);
    }

    @Test
    void getTile() {
        assertNotNull(gameObject.getTile());
    }

    @Test
    void isDead() {
        assertFalse(gameObject.isDead());
        gameObject.die();
        assertTrue(gameObject.isDead());
    }

    @Test
    void die() {
        gameObject.die();
        assertTrue(gameObject.isDead());
    }

    @Test
    void update() {
    }

    @Test
    void initializeComponents() {
    }

    @Test
    void getComponents() {
        assertNotNull(gameObject.getComponents());
    }

    @Test
    void getComponentByType() {
        assertNotNull(gameObject.getComponentByType(ComponentType.TRANSFORM_COMPONENT).get(), "Should return a TransformComponent");
    }

    @Test
    void setAsPlayer() {
        assertFalse(gameObject.isPlayer(), "Should not return true");
        gameObject.setAsPlayer(true);
        assertTrue(gameObject.isPlayer(), "Should return true as object has been set to player");
    }

    @Test
    void isPlayer() {
        assertFalse(gameObject.isPlayer(), "Should not return true");
        gameObject.setAsPlayer(true);
        assertTrue(gameObject.isPlayer(), "Should return true as object has been set to player");
    }
}