package engine.entities.components;

import engine.controllers.Direction;
import engine.entities.components.ComponentEvent.CollisionEvent;
import engine.entities.components.ComponentEvent.ComponentEvent;
import engine.entities.components.ComponentEvent.MoveEvent;
import engine.entities.gameobjects.GameObject;
import engine.entities.gameobjects.ImpUpdatableGameObject;
import engine.entities.gameobjects.Sprite;
import engine.world.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CollisionComponentTest {

    CollisionComponent collisionComponent;
    ImpUpdatableGameObject testObject;
    TestComponent testComponent;
    TransformComponent testTransformComponent;

    @BeforeEach
    void setUp(){
        Tile testTile = new Tile(1,1, new Sprite(1));
        Tile testTile2 = new Tile(1,2, new Sprite(1));
        testTile2.setGameObject(new GameObject(new ArrayList<>()));
        testTile.setUp(testTile2);
        testTransformComponent = new TransformComponent(testTile);
        collisionComponent = new CollisionComponent(testTransformComponent);
        testComponent = new TestComponent(ComponentType.TRANSFORM_COMPONENT) {
            boolean collided;

            @Override
            public Boolean result() {
                return collided;
            }

            @Override
            public void handle(ComponentEvent event) {
                if(event instanceof CollisionEvent){
                    collided = true;
                }
            }
        };
        ArrayList<ScriptableComponent> list = new ArrayList<>();
        list.add(collisionComponent);
        list.add(testComponent);
        testObject = new ImpUpdatableGameObject(list);
    }

    @Test
    void update() {
        collisionComponent.update(testObject);
        assertFalse((Boolean) testComponent.result(), "Should not receive collided");
    }

    @Test
    void handle() {
        collisionComponent.handle(new MoveEvent(Direction.UP));
        testObject.update();
        assertTrue((Boolean) testComponent.result(), "Should receive collided");
    }

    @Test
    void innit() {
    }

    @Test
    void cleanUp() {
    }

    @Test
    void collided() {
        collisionComponent.handle(new MoveEvent(Direction.UP));
        assertEquals(Direction.UP, collisionComponent.collided());
    }

    @Test
    void clearCollided() {
    }
}