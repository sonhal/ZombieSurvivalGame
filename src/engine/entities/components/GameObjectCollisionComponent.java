package engine.entities.components;
import engine.controllers.Direction;
import engine.entities.components.ComponentEvent.*;
import engine.entities.components.interfaces.CollisionComponent;
import engine.entities.components.interfaces.TransformComponent;
import engine.entities.gameobjects.interfaces.GameObject;
import engine.world.Tile;

/**
 * General collision detection Component. Signals other components if a Collision has occurred with the GameObject.
 */
public class GameObjectCollisionComponent extends CollisionComponent {

    private Direction inputDirection;

    @Override
    public void update(GameObject gameObject){
        if (inputDirection != null) {
            if(collisionDetect(gameObject.getTransformComponent().getCurrentTile(), inputDirection)){
                sendMessageToAllComponents(gameObject.getComponents(), new CollisionEvent(inputDirection));
            }
            else {
                sendMessageToAllComponents(gameObject.getComponents(),
                        new CollisionEvent(null));
            }
            inputDirection = null;
        }
    }

    @Override
    public void handle(ComponentEvent event) {
        if(event instanceof MoveEvent){
            inputDirection = ((MoveEvent) event).getDirection();
        }
        if(event instanceof CheckForCollisionEvent){
            inputDirection = ((CheckForCollisionEvent) event).getDirection();
        }
    }

    @Override
    public void innit(GameObject gameObject) {
        //Do nothing
    }

    @Override
    public void cleanUp(GameObject gameObject) {
        //Do nothing
    }

    private boolean collisionDetect(Tile currentTile, Direction direction) {
        return (currentTile.getTileInDirection(direction).getGameObject() != null);
    }
}
