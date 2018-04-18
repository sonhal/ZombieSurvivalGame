package engine.entities;

import engine.controllers.Direction;
import engine.entities.composites.ComponentType;
import engine.entities.composites.GraphicsComponent;
import engine.entities.composites.Sprite;
import engine.entities.composites.TransformComponent;
import engine.entities.interfaces.IGameObject;
import engine.entities.world.Tile;
import engine.services.ComponentService;
import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameObjectTest {
    private IGameObject gameObject;
    private Sprite sprite;
    private Tile testTile;
    private TransformComponent transformComponent;

    @BeforeEach
    void setUp() {
        sprite = new Sprite(2);
        testTile = new Tile(0,0,sprite);
        gameObject = GameObjectFactory.createStaticGameObject(sprite, testTile);

        if(ComponentService.getComponentByType(gameObject.getComponents(), ComponentType.INPUT_COMPONENT).isPresent()){
            transformComponent = (TransformComponent)
                    ComponentService.getComponentByType(gameObject.getComponents(),
                            ComponentType.INPUT_COMPONENT).get();
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void moveUp() {
        Tile startTile = new Tile(1,1, new Sprite(1));
        Tile up = new Tile(1,2, new Sprite(1));

        startTile.setUp(up);
        transformComponent.setCurrentTile(startTile);
        startTile.setGameObject(gameObject);
        assertEquals(transformComponent.getCurrentTile(), up, "Up failed");
    }

    @Test
    void moveDown() {
        Tile startTile = new Tile(1,2, new Sprite(1));
        Tile down = new Tile(1,2, new Sprite(1));
        startTile.setDown(down);
        transformComponent.setCurrentTile(startTile);
        assertEquals(transformComponent.getCurrentTile(), down, "Down failed");
    }

    @Test
    void moveLeft() {
        Tile startTile = new Tile(1,2, new Sprite(1));
        Tile left = new Tile(1,2, new Sprite(1));
        startTile.setLeft(left);


        transformComponent.setCurrentTile(startTile);
        startTile.setGameObject(gameObject);


        assertEquals(transformComponent.getCurrentTile(), left, "Left failed");
    }

    @Test
    void moveRight() {
        Tile startTile = new Tile(1,2, new Sprite(1));
        Tile right = new Tile(1,2, new Sprite(1));
        startTile.setRight(right);

        transformComponent.setCurrentTile(startTile);
        startTile.setGameObject(gameObject);

        assertEquals(transformComponent.getCurrentTile(), right, "Right failed");
    }

    @Test
    void getSprite() {
        assertEquals(gameObject.getSprite(), sprite, "Wrong Sprite returned");
    }

    @Test
    void getTile() {
        Tile startTile = new Tile(1,2, new Sprite(1));
        transformComponent.setCurrentTile(startTile);
        startTile.setGameObject(gameObject);
        assertEquals(startTile, gameObject.getTile());
    }


}