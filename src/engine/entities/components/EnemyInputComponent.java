package engine.entities.components;

import engine.controllers.Direction;
import engine.entities.components.ComponentEvent.*;
import engine.entities.components.componentEventHandlers.Message;
import engine.entities.gameobjects.interfaces.IGameObject;

public class EnemyInputComponent extends ScriptableComponent{

    private IGameObject target;
    private TransformComponent targetTransformComponent;
    private TransformComponent gameObjectTransformComponent;
    private Direction collisionHasOccurred;
    private double lastEventSent;
    private double sendEventDelay;


    public EnemyInputComponent(IGameObject target, double sendEventDelay){
        super(ComponentType.INPUT_COMPONENT);
        this.target = target;
        this.sendEventDelay = sendEventDelay;
    }



    private Direction getDirectionAgainstPlayer(IGameObject gameObject){

        //Bad collision avoiding AI
        if (collisionHasOccurred != null){
            if(collisionHasOccurred == Direction.UP){
                return Direction.LEFT;
            }
            if(collisionHasOccurred == Direction.DOWN){
                return Direction.RIGHT;
            }
            if(collisionHasOccurred == Direction.LEFT){
                return Direction.UP;
            }
            if(collisionHasOccurred == Direction.RIGHT){
                return Direction.DOWN;
            }
        }
        int targetX = targetTransformComponent.getCurrentTile().getCordX();
        int targetY = targetTransformComponent.getCurrentTile().getCordY();
        int enemyX = gameObjectTransformComponent.getCurrentTile().getCordX();
        int enemyY = gameObjectTransformComponent.getCurrentTile().getCordY();

        if (targetX < enemyX){
            if(targetY < enemyY){
                return Direction.DOWN;
            }
            else if (targetY == enemyY){
                return Direction.LEFT;
            }
            else return Direction.UP;
        }
        else {
            if(targetY < enemyY){
                return Direction.DOWN;
            }
            else if (targetY == enemyY){
                return Direction.RIGHT;
            }
            else return Direction.UP;
        }
    }

    private Direction isPlayerInRange(IGameObject gameObject){
        if(collisionHasOccurred != null){
            if(gameObjectTransformComponent.getCurrentTile().getTileInDirection(collisionHasOccurred).getGameObject() == target){
                return collisionHasOccurred;
            }
        }
        return null;
    }

    @Override
    public void update(IGameObject gameObject) {
        //If Player is in range, attack Player
        if(isPlayerInRange(gameObject) != null){
            sendMessageToAllComponents(gameObject.getComponents(), new AttackEvent(isPlayerInRange(gameObject)));
        }
        //If not, Move towards Player
        else {
            if(canActivate(sendEventDelay, lastEventSent)){
                sendMessageToAllComponents(gameObject.getComponents(), new MoveEvent(getDirectionAgainstPlayer(gameObject)));
                lastEventSent = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void handle(ComponentEvent event) {
        if(event instanceof CollisionEvent){
            this.collisionHasOccurred = ((CollisionEvent) event).collisonDirection();
        }

    }

    @Override
    public void innit(IGameObject gameObject) {
        //Gets reference to Players TransformComponent
        if(getComponentByType(target.getComponents(), ComponentType.TRANSFORM_COMPONENT).isPresent()){
            this.targetTransformComponent =
                    (TransformComponent) getComponentByType(target.getComponents()
                            , ComponentType.TRANSFORM_COMPONENT).get();
        }

        //Gets reference to its own TransformComponent
        if(getComponentByType(gameObject.getComponents(), ComponentType.TRANSFORM_COMPONENT).isPresent()){
            this.gameObjectTransformComponent =
                    (TransformComponent) getComponentByType(gameObject.getComponents()
                            , ComponentType.TRANSFORM_COMPONENT).get();
        }

        lastEventSent = System.currentTimeMillis();
    }

    @Override
    public void cleanUp(IGameObject gameObject) {

    }


}
