package engine.entities.components;

import engine.controllers.Direction;
import engine.entities.components.ComponentEvent.*;
import engine.entities.components.interfaces.InputComponent;
import engine.entities.components.interfaces.TransformComponent;
import engine.entities.gameobjects.interfaces.GameObject;
import engine.services.pathfinder.*;
import engine.world.Tile;

import java.util.concurrent.Future;

public class EnemyInputComponent extends InputComponent {

    private GameObject target;
    private TransformComponent targetTransformComponent;
    private TransformComponent gameObjectTransformComponent;
    private Direction collisionHasOccurred;
    private double lastEventSent;
    private double sendEventDelay;
    private double lastPathSearch;
    private Path enemyPath;
    private PathSearchService pathSearchService;
    private Future<Path> currentPath;
    private Future<Path> nextPath;


    public EnemyInputComponent(GameObject target, PathSearchService pathSearchService, double sendEventDelay){
        this.target = target;
        this.sendEventDelay = sendEventDelay;
        this.pathSearchService = pathSearchService;
    }



    private Direction getDirectionAgainstPlayer(GameObject gameObject){
        if(nextPath != null && nextPath.isDone()){
            currentPath = nextPath;
        }

        if(currentPath != null && currentPath.isDone()){
            try {
                if(currentPath.get() != null){
                    Direction nextStep = currentPath.get().getNextStepDirection();
                    if(nextStep == null){
                        System.out.println("Empty step");
                        return Direction.UP;
                    }
                    return nextStep;
                }
                else {
                    System.out.println("NULL path");
                }
            }catch (Exception err){
                err.printStackTrace();
            }
        }
        return Direction.UP;

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

        if(canActivate(3000, lastPathSearch)){
            Tile currentTile = gameObject.getTransformComponent().getCurrentTile();
            nextPath = pathSearchService.getNewPath(currentTile.getCordX(), currentTile.getCordY(),
                    targetTransformComponent.getCurrentTile().getCordX(), targetTransformComponent.getCurrentTile().getCordY());
            lastPathSearch = System.currentTimeMillis();
        }


        //If Player is in range, attack Player
        if(isPlayerInRange(gameObject) != null){
            sendMessageToAllComponents(gameObject.getComponents(), new AttackEvent(isPlayerInRange(gameObject)));
        }
        //If not, Move towards Player
        else {
            if(canActivate(sendEventDelay, lastEventSent)){
                Direction direction  = getDirectionAgainstPlayer(gameObject);
                sendMessageToAllComponents(gameObject.getComponents(), new MoveEvent(direction));
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
        lastPathSearch = System.currentTimeMillis();
    }

    @Override
    public void cleanUp(GameObject gameObject) {

    }


}
