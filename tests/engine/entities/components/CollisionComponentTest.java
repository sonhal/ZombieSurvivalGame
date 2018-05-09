package engine.entities.components;

import engine.controllers.Direction;
import engine.entities.components.ComponentEvent.CollisionEvent;
import engine.entities.components.ComponentEvent.ComponentEvent;
import engine.entities.components.ComponentEvent.MoveEvent;
import engine.entities.gameobjects.StaticGameObject;
import engine.entities.gameobjects.Sprite;
import engine.entities.gameobjects.UpdatableGameObject;
import engine.entities.gameobjects.interfaces.IUpdatableGameObject;
import engine.world.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CollisionComponentTest {

    GameObjectCollisionComponent collisionComponent;
    IUpdatableGameObject testObject;
    TestComponent testComponent;
    StaticTransformComponent testTransformComponent;

    @BeforeEach
    void setUp(){
        Tile testTile = new Tile(1,1, new Sprite(1));
        Tile testTile2 = new Tile(1,2, new Sprite(1));
        testTransformComponent = new StaticTransformComponent(testTile);
        StaticGameObject testObject2 = new StaticGameObject.Builder(new StaticTransformComponent(testTile2))
                .build();
        testTile.setUp(testTile2);
        collisionComponent = new GameObjectCollisionComponent();
        testComponent = new TestComponent() {
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

        testObject = new UpdatableGameObject.Builder(testTransformComponent)
                .addComponent(collisionComponent)
                .addComponent(testComponent)
                .build();
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

}