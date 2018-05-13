package engine.entities.components;

import engine.controllers.Direction;
import engine.entities.components.ComponentEvent.*;
import engine.entities.components.interfaces.InputComponent;
import engine.entities.components.interfaces.TransformComponent;
import engine.entities.gameobjects.interfaces.GameObject;
import engine.services.pathfinder.*;
import engine.world.Tile;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class EnemyInputComponent extends InputComponent{

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
    private Optional<Future<Path>> nextPath;
    private Path path;


    public EnemyInputComponent(GameObject target, PathSearchService pathSearchService, double sendEventDelay){
        this.target = target;
        this.sendEventDelay = sendEventDelay;
        this.pathSearchService = pathSearchService;
    }



    private Direction getNextDirection(GameObject gameObject){
        Direction nextStep = null;

        if(nextPath != null && nextPath.isPresent()){
            currentPath = nextPath.get();
            nextPath = null;
        }

        if(currentPath !=null && currentPath.isDone()){
            try {
                path = currentPath.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        if(path != null){
            nextStep = path.getNextStepDirection();
        }
        else {
            System.out.println("NULL path");
        }

        if(nextStep != null){
            return nextStep;
        }
        System.out.println("Close step active");
        return getDirectionAgainstTile(gameObject, target.getTransformComponent().getCurrentTile());
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
        Random random = new Random();


        if(canActivate(2000 +(random.nextInt(1000)), lastPathSearch)){
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
                Direction direction  = getNextDirection(gameObject);
                if(direction != null){
                    sendMessageToAllComponents(gameObject.getComponents(), new MoveEvent(direction));
                }
                lastEventSent = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void handle(ComponentEvent event) {
        if(event instanceof CollisionEvent){
            this.collisionHasOccurred = ((CollisionEvent) event).collisonDirection();
        }
        if(event instanceof  DeathEvent){
            if (currentPath != null){
                currentPath.cancel(true);
            }
            if(nextPath != null){
                nextPath.ifPresent(pathFuture -> pathFuture.cancel(true));
            }

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

    private Direction getRandomDirection(){
        Random random = new Random();
        int dir = random.nextInt(4);
        switch (dir){
            case 1: return Direction.UP;
            case 2: return Direction.DOWN;
            case 3: return Direction.LEFT;
            default: return Direction.RIGHT;
        }
    }

    private Direction getDirectionAgainstTile(GameObject gameObject, Tile tile){
        int targetX = tile.getCordX();
        int targetY = tile.getCordY();

        int thisX = gameObject.getTransformComponent().getCurrentTile().getCordX();
        int thisY = gameObject.getTransformComponent().getCurrentTile().getCordY();

        if (thisX == targetX ) {
            if(thisY < targetY){
                return Direction.DOWN;
            }
            else {
                return Direction.UP;
            }
        }
        if(thisX < targetX){
            return Direction.RIGHT;
        }
        else {
            return Direction.LEFT;
        }

    }
}
