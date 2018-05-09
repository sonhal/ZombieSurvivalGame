package engine.entities.components;

import engine.controllers.Direction;
import engine.entities.components.ComponentEvent.*;
import engine.entities.components.interfaces.InputComponent;
import engine.entities.components.interfaces.TransformComponent;
import engine.entities.gameobjects.interfaces.GameObject;

public class EnemyInputComponent extends InputComponent {

    private GameObject target;
    private TransformComponent targetTransformComponent;
    private TransformComponent gameObjectTransformComponent;
    private Direction collisionHasOccurred;
    private double lastEventSent;
    private double sendEventDelay;


    public EnemyInputComponent(GameObject target, double sendEventDelay){
        this.target = target;
        this.sendEventDelay = sendEventDelay;
    }



    private Direction getDirectionAgainstPlayer(GameObject gameObject){

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

    private Direction isPlayerInRange(GameObject gameObject){
        if(collisionHasOccurred != null){
            if(gameObjectTransformComponent.getCurrentTile().getTileInDirection(collisionHasOccurred).getGameObject() == target){
                return collisionHasOccurred;
            }
        }
        return null;
    }

    @Override
    public void update(GameObject gameObject) {
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
    public void innit(GameObject gameObject) {
        //Gets reference to Players StaticTransformComponent
        this.targetTransformComponent = target.getTransformComponent();

        //Gets reference to its own StaticTransformComponent
        this.gameObjectTransformComponent = gameObject.getTransformComponent();

        lastEventSent = System.currentTimeMillis();
    }

    @Override
    public void cleanUp(GameObject gameObject) {

    }


}
