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
    private Future<Path> nextPath;
    private Path path;
    private boolean waitingForPath;
    private static final double WAIT_TIME = 800;
    private double timeWaited;


    public EnemyInputComponent(GameObject target, PathSearchService pathSearchService, double sendEventDelay){
        this.target = target;
        this.sendEventDelay = sendEventDelay;
        this.pathSearchService = pathSearchService;
    }



    private Direction getNextDirection(GameObject gameObject){
        if(path != null){
            Direction nextStep = path.getNextStepDirection();
            if(nextStep != null){
                waitingForPath = true;
                return nextStep;

            }
            else {
                path = null;
                waitingForPath = false;
            }
        }
        else {
            if( nextPath != null && (nextPath.isDone())){
                try {
                    waitingForPath = false;
                    path = nextPath.get();
                    return path.getNextStepDirection();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
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

        if((canActivate(3000, lastPathSearch)) && (!waitingForPath)){
            lastPathSearch = System.currentTimeMillis();

            Tile currentTile = gameObject.getTransformComponent().getCurrentTile();
            Optional<Future<Path>> oFuturePath = pathSearchService.getNewPath(currentTile.getCordX(), currentTile.getCordY(),
                    targetTransformComponent.getCurrentTile().getCordX(), targetTransformComponent.getCurrentTile().getCordY());
           if(oFuturePath.isPresent()){
               System.out.println("Got nextpath");
               waitingForPath = true;
               nextPath = oFuturePath.get();
           }

        }



        //If Player is in range, attack Player
        if(isPlayerInRange(gameObject) != null){
            System.out.println("attack");
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
