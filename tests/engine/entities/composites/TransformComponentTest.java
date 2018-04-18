package engine.entities.composites;

import engine.controllers.Direction;
import engine.entities.GameObjectFactory;
import engine.entities.Sprite;
import engine.entities.interfaces.IGameObject;
import engine.entities.world.Tile;
import engine.services.ComponentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransformComponentTest {

    private TransformComponent transformComponent;
    private Tile tile;

    @BeforeEach
    void setUp() {
        tile = new Tile(1,1,new Sprite(1));
        IGameObject gameObject = GameObjectFactory.createStaticGameObject(new Sprite(1), tile);
        if(ComponentService.getComponentByType(gameObject.getComponents(), ComponentType.INPUT_COMPONENT).isPresent()){
            transformComponent = (TransformComponent)
                    ComponentService.getComponentByType(gameObject.getComponents(),
                            ComponentType.INPUT_COMPONENT).get();
        }
    }

    @Test
    void getCurrentTile() {
        assertEquals(transformComponent.getCurrentTile(), tile);
    }

    @Test
    void setCurrentTile() {
        Tile newTile = new Tile(1,2, new Sprite(1));
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
        Tile upTile = new Tile(2,1, new Sprite(1));
        tile.setUp(upTile);
        transformComponent.handle(new Message(ComponentEvent.MOVE_EVENT, Direction.UP));
        assertEquals(upTile, transformComponent.getCurrentTile());
    }

}