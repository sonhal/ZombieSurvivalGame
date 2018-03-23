package engine.entities.composites;

import engine.controllers.Direction;
import engine.entities.GameObject;
import engine.entities.world.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransformComponentTest {

    private TransformComponent transformComponent;
    private Tile tile;

    @BeforeEach
    void setUp() {
        GameObject gameObject = new GameObject();
        transformComponent = gameObject.getTransformComponent();
        tile = new Tile(1,1);
        this.transformComponent.setCurrentTile(tile);
    }

    @Test
    void getCurrentTile() {
        assertEquals(transformComponent.getCurrentTile(), tile);
    }

    @Test
    void setCurrentTile() {
        Tile newTile = new Tile(1,2);
        transformComponent.setCurrentTile(newTile);

        assertEquals(transformComponent.getCurrentTile(), newTile);
    }

    @Test
    void getFacingDirection() {
        transformComponent.setFacingDirection(Direction.UP);
        assertEquals(transformComponent.getFacingDirection(), Direction.UP);
    }

    @Test
    void setFacingDirection() {
        transformComponent.setFacingDirection(Direction.UP);
        assertEquals(transformComponent.getFacingDirection(), Direction.UP);
    }

    @Test
    void move() {
        Tile upTile = new Tile(2,1);
        tile.setUp(upTile);
        transformComponent.move(Direction.UP);
        assertEquals(upTile, transformComponent.getCurrentTile());
    }

}