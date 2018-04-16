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

    CollisionComponent() {
        super(ComponentType.COLLISION_COMPONENT);
    }

    @Override
    public void update(IGameObject gameObject) {
        if (inputDirection != null) {
            clearCollided();
            Optional<ScriptableComponent> optionalComponent =
                    getComponentByType(gameObject.getComponents(), ComponentType.TRANSFORM_COMPONENT);
            if (optionalComponent.isPresent()) {
                TransformComponent transformComponent = (TransformComponent) optionalComponent.get();
                collisionDetect(transformComponent.getCurrentTile(), inputDirection);
            }
        }
    }

    @Override
    public void handle(Message message) {
        if(message.type == ComponentType.INPUT_COMPONENT){
            inputDirection = (Direction) message.message;
        }
    }

    @Override
    public void innit(IGameObject gameObject) {
        //Do nothing
    }

    public Direction collided() {
        return collided;
    }

    public void clearCollided() {
        collided = null;
    }

    private void collisionDetect(Tile currentTile, Direction direction) {

        switch (direction) {
            case RIGHT:
                if (currentTile.getRight().getGameObject() != null) {
                    collided = Direction.RIGHT;
                }
                break;
            case LEFT:
                if (currentTile.getLeft().getGameObject() != null) {
                    collided = Direction.LEFT;
                }
                break;
            case UP:
                if (currentTile.getUp().getGameObject() != null) {
                    collided = Direction.UP;
                }
                break;
            case DOWN:
                if (currentTile.getDown().getGameObject() != null) {
                    collided = Direction.DOWN;
                }
                break;
            default:
                collided = null;
        }
    }
}
