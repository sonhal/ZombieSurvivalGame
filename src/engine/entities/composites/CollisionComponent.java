package engine.entities.composites;
import engine.controllers.Direction;
import engine.entities.interfaces.Collidable;
import engine.entities.interfaces.IGameObject;
import engine.entities.world.Tile;

import java.io.Serializable;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;

public class CollisionComponent extends ScriptableComponent {

    private Direction collided;
    private Direction inputDirection;
    private TransformComponent gameObjectTransformComponent;

    public CollisionComponent() {
        super(ComponentType.COLLISION_COMPONENT);
    }

    @Override
    public void update(IGameObject gameObject){
        if (inputDirection != null) {
            clearCollided();
            if(collisionDetect(gameObjectTransformComponent.getCurrentTile(), inputDirection) != null ){
                sendMessageToAllComponents(gameObject.getComponents(), new Message(ComponentEvent.COLLISION_EVENT,
                        collisionDetect(gameObjectTransformComponent.getCurrentTile(), inputDirection)));
            }
            else {
                sendMessageToAllComponents(gameObject.getComponents(),
                        new Message(ComponentEvent.COLLISION_EVENT,null));
            }
        }
    }

    @Override
    public void handle(Message message) {
        if(message.event == ComponentEvent.MOVE_EVENT){
            inputDirection = (Direction) message.message;
        }
    }

    @Override
    public void innit(IGameObject gameObject) {
        //Gets reference to its own TransformComponent
        if(getComponentByType(gameObject.getComponents(), ComponentType.TRANSFORM_COMPONENT).isPresent()){
            this.gameObjectTransformComponent =
                    (TransformComponent) getComponentByType(gameObject.getComponents()
                            , ComponentType.TRANSFORM_COMPONENT).get();
        }
    }

    @Override
    public void cleanUp(IGameObject gameObject) {

    }

    public Direction collided() {
        return collided;
    }

    public void clearCollided() {
        collided = null;
    }

    private Direction collisionDetect(Tile currentTile, Direction direction) {
        switch (direction) {
            case RIGHT:
                if (currentTile.getRight().getGameObject() != null) {
                    return Direction.RIGHT;
                }
                break;
            case LEFT:
                if (currentTile.getLeft().getGameObject() != null) {
                    return Direction.LEFT;
                }
                break;
            case UP:
                if (currentTile.getUp().getGameObject() != null) {
                    return Direction.UP;
                }
                break;
            case DOWN:
                if (currentTile.getDown().getGameObject() != null) {
                    return Direction.DOWN;
                }
                break;
        }
        return null;
    }
}
