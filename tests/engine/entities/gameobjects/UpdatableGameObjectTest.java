package engine.entities.gameobjects;

import engine.entities.components.StaticTransformComponent;
import engine.entities.components.interfaces.TransformComponent;
import engine.world.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpdatableGameObjectTest {

    UpdatableGameObject gameObject;
    Tile testTile;
    TransformComponent transformComponent;


    @BeforeEach
    void setUp() {
        testTile = new Tile(1,1, new Sprite(1));
        transformComponent = new StaticTransformComponent(testTile);
        gameObject = new UpdatableGameObject.Builder(transformComponent).build();
    }

    @Test
    void testMVPUpdatableGameObject(){
        testTile = new Tile(1,1, new Sprite(1));
        transformComponent = new StaticTransformComponent(testTile);
        gameObject = new UpdatableGameObject.Builder(transformComponent).build();
        assertNotNull(gameObject);
    }

    @Test
    void getTransformComponent() {
        assertNotNull(gameObject.getTransformComponent());
    }

    @Test
    void isDead() {
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
        gameObject.update();
    }



    @Test
    void setAsPlayer() {
        gameObject.setAsPlayer(true);
        assertTrue(gameObject.isPlayer());
    }

}