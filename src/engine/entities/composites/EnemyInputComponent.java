package engine.entities.composites;

import engine.controllers.Direction;
import engine.entities.interfaces.IGameObject;

public class EnemyInputComponent extends ScriptableComponent{

    private IGameObject target;
    private TransformComponent playerTransformComponent;
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
        int playerX = playerTransformComponent.getCurrentTile().getCordX();
        int playerY = playerTransformComponent.getCurrentTile().getCordY();
        int enemyX = gameObjectTransformComponent.getCurrentTile().getCordX();
        int enemyY = gameObjectTransformComponent.getCurrentTile().getCordY();

        if (playerX < enemyX){
            if(playerY < enemyY){
                return Direction.DOWN;
            }
            else if (playerY == enemyY){
                return Direction.LEFT;
            }
            else return Direction.UP;
        }
        else {
            if(playerY < enemyY){
                return Direction.DOWN;
            }
            else if (playerY == enemyY){
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
            sendMessageToAllComponents(gameObject.getComponents(), new Message(ComponentEvent.ATTACK_EVENT, isPlayerInRange(gameObject)));
        }
        //If not, Move towards Player
        else {
            if(canActivate(sendEventDelay, lastEventSent)){
                sendMessageToAllComponents(gameObject.getComponents(), new Message(ComponentEvent.MOVE_EVENT, getDirectionAgainstPlayer(gameObject)));
                lastEventSent = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void handle(Message message) {
        if(message.event == ComponentEvent.COLLISION_EVENT){
            this.collisionHasOccurred = (Direction) message.message;
        }

    }

    @Override
    public void innit(IGameObject gameObject) {
        //Gets reference to Players TransformComponent
        if(getComponentByType(target.getComponents(), ComponentType.TRANSFORM_COMPONENT).isPresent()){
            this.playerTransformComponent =
                    (TransformComponent) getComponentByType(target.getComponents()
                            , ComponentType.TRANSFORM_COMPONENT).get();
        }

        //Gets reference to its own TransformComponent
        if(getComponentByType(gameObject.getComponents(), ComponentType.TRANSFORM_COMPONENT).isPresent()){
            this.gameObjectTransformComponent =
                    (TransformComponent) getComponentByType(target.getComponents()
                            , ComponentType.TRANSFORM_COMPONENT).get();
        }

        lastEventSent = System.currentTimeMillis();
    }

    @Override
    public void cleanUp(IGameObject gameObject) {

    }


}
