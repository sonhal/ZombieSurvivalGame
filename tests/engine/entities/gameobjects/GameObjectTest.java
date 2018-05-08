package engine.entities.gameobjects;

import engine.entities.components.ComponentType;
import engine.entities.components.ScriptableComponent;
import engine.entities.components.TransformComponent;
import engine.world.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameObjectTest {

    GameObject gameObject;
    Tile testTile;
    TransformComponent testTransformComponent;

    @BeforeEach
    void setUp() {
        testTile = new Tile(1,1, new Sprite(1));
        testTransformComponent = new TransformComponent(testTile);
        ArrayList<ScriptableComponent> list = new ArrayList<>();
        list.add(testTransformComponent);
        gameObject = new GameObject(list);
    }

    @Test
    void getTile() {
        assertNotNull(gameObject.getTile(), "Should return a Tile object");
    }

    @Test
    void getComponentByType() {
        assertNotNull(gameObject.getComponentByType(ComponentType.TRANSFORM_COMPONENT).get(),
                "Should return a Transform Component");
    }

    @Test
    void getComponents() {
        assertNotNull(gameObject.getComponents().get(0), "Should return a Transform component");
    }

}