package engine.entities.gameobjects;

import engine.entities.components.ScriptableComponent;
import engine.entities.components.StaticGraphicsComponent;
import engine.entities.components.StaticTransformComponent;
import engine.entities.components.interfaces.GraphicsComponent;
import engine.entities.components.interfaces.TransformComponent;
import engine.world.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameObjectTest {

    StaticGameObject gameObject;
    Tile testTile;
    StaticTransformComponent testTransformComponent;

    @BeforeEach
    void setUp() {
        testTile = new Tile(1,1, new Sprite(1));
        testTransformComponent = new StaticTransformComponent(testTile);
        StaticGraphicsComponent graphicsComponent = new StaticGraphicsComponent(new Sprite(1));
        gameObject = new StaticGameObject.Builder(testTransformComponent)
                .addComponent(graphicsComponent)
                .build();
    }

    @Test
    void getTile() {
        assertNotNull(gameObject.getTransformComponent().getCurrentTile(), "Should return a Tile object");
    }

    @Test
    void getComponentByType() {
        assertNotNull(gameObject.getComponentByType(TransformComponent.class).get(),
                "Should return a Transform Component");
        assertNotNull(gameObject.getComponentByType(GraphicsComponent.class).get(),
                "Should return a Graphic Component");
    }

    @Test
    void getComponents() {
        assertNotNull(gameObject.getComponents().get(0), "Should return a Transform component");
    }

}