package engine.entities;

import engine.controllers.Direction;
import engine.entities.composites.GraphicsComponent;
import engine.entities.composites.Sprite;
import engine.entities.composites.TransformComponent;
import engine.entities.world.Tile;
import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameObjectTest {
    private GameObject gameObject;
    private TransformComponent transformComponent;
    private GraphicsComponent graphicsComponent;
    private Sprite sprite;

    @BeforeEach
    void setUp() {
        sprite = new Sprite(2);
        graphicsComponent = new GraphicsComponent(sprite);
        gameObject = new GameObject(transformComponent, graphicsComponent);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getGraphicsComponent() {
        assertEquals(graphicsComponent, gameObject.getGraphicsComponent());
    }

    @Test
    void getTransformComponent() {
        assertEquals(transformComponent, gameObject.getTransformComponent());
    }

    @Test
    void moveUp() {
        Tile startTile = new Tile(1,1, new Sprite(1));
        Tile up = new Tile(1,2, new Sprite(1));

        startTile.setUp(up);




        gameObject.getTransformComponent().setCurrentTile(startTile);
        startTile.setGameObject(gameObject);

        gameObject.move(Direction.UP);
        assertEquals(gameObject.getTransformComponent().getCurrentTile(), up, "Up failed");
    }

    @Test
    void moveDown() {
        Tile startTile = new Tile(1,2, new Sprite(1));
        Tile down = new Tile(1,2, new Sprite(1));
        startTile.setDown(down);




        gameObject.getTransformComponent().setCurrentTile(startTile);
        startTile.setGameObject(gameObject);

        gameObject.move(Direction.DOWN);
        assertEquals(gameObject.getTransformComponent().getCurrentTile(), down, "Down failed");
    }

    @Test
    void moveLeft() {
        Tile startTile = new Tile(1,2, new Sprite(1));
        Tile left = new Tile(1,2, new Sprite(1));
        startTile.setLeft(left);


        gameObject.getTransformComponent().setCurrentTile(startTile);
        startTile.setGameObject(gameObject);


        gameObject.move(Direction.LEFT);
        assertEquals(gameObject.getTransformComponent().getCurrentTile(), left, "Left failed");
    }

    @Test
    void moveRight() {
        Tile startTile = new Tile(1,2, new Sprite(1));
        Tile right = new Tile(1,2, new Sprite(1));
        startTile.setRight(right);



        gameObject.getTransformComponent().setCurrentTile(startTile);
        startTile.setGameObject(gameObject);


        gameObject.move(Direction.RIGHT);
        assertEquals(gameObject.getTransformComponent().getCurrentTile(), right, "Right failed");


    }

    @Test
    void getSprite() {
        assertEquals(gameObject.getSprite(), sprite, "Wrong Sprite returned");
    }

    @Test
    void getTile() {
        Tile startTile = new Tile(1,2, new Sprite(1));
        gameObject.getTransformComponent().setCurrentTile(startTile);
        startTile.setGameObject(gameObject);
        assertEquals(startTile, gameObject.getTile());

    }


}