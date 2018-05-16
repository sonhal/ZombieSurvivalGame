package engine.entities.components;

import engine.controllers.Direction;
import engine.entities.components.ComponentEvent.ComponentEvent;
import engine.entities.components.ComponentEvent.DeathEvent;
import engine.entities.components.ComponentEvent.MoveEvent;
import engine.entities.components.interfaces.TransformComponent;
import engine.entities.gameobjects.interfaces.GameObject;
import engine.world.Tile;

/**
 * StaticTransformComponents are to be used with GameObjects when the transformComponent doeas not need to
 * be updated by the gameloop. As is the case with StaticGameObjects.
 */
public class StaticTransformComponent extends TransformComponent {

    private Tile currentTile;
    private Direction facingDirection;
    protected Direction move;
    protected boolean removeSelf;

    public StaticTransformComponent(Tile connectedTile){
        currentTile = connectedTile;
    }


    @Override
    public void update(GameObject gameObject) {
        if(removeSelf){
            cleanUp(gameObject);
        }
        //Does nothing
    }

    @Override
    public void handle(ComponentEvent event) {
        if(event instanceof MoveEvent){
            move = ((MoveEvent)event).getDirection();
        }
        if(event instanceof DeathEvent){
            removeSelf = true;
        }
    }

    @Override
    public void innit(GameObject gameObject) {
        this.currentTile.setGameObject(gameObject);
    }

    @Override
    public void cleanUp(GameObject gameObject) {
        if(currentTile != null){
            if(getCurrentTile().getGameObject() == gameObject){
                getCurrentTile().clearGameObject();
            }
            currentTile = null;
        }
    }

    @Override
    public Tile getCurrentTile() {
        return currentTile;
    }

    @Override
    public void setCurrentTile(Tile tile) {
        this.currentTile = tile;
    }

    @Override
    public Direction getFacingDirection() {
        if (facingDirection == null){return Direction.UP;}
        return facingDirection;
    }

    @Override
    public void setFacingDirection(Direction facingDirection) {
        this.facingDirection = facingDirection;
    }

    private void switchTilePlacement(Tile oldTile, Tile newTile, GameObject gameObject){
        oldTile.clearGameObject();
        setCurrentTile(newTile);
        currentTile.setGameObject(gameObject);
    }

    @Override
    public void move(Direction direction, GameObject gameObject) {
        if (currentTile != null){
            Tile lastTile = currentTile;
            Tile newTile = currentTile.getTileInDirection(direction);
            switch (direction) {
                case UP:
                    if(currentTile.getUp() != null){
                        switchTilePlacement(lastTile, newTile, gameObject);
                        this.facingDirection = Direction.UP;}
                    break;
                case DOWN:
                    if(currentTile.getDown() != null){
                        switchTilePlacement(lastTile, newTile, gameObject);
                        this.facingDirection = Direction.DOWN;}
                    break;
                case LEFT:
                    if(currentTile.getLeft() != null){
                        switchTilePlacement(lastTile, newTile, gameObject);
                        this.facingDirection = Direction.LEFT;}
                    break;
                case RIGHT:
                    if(currentTile.getRight() != null){
                        switchTilePlacement(lastTile, newTile, gameObject);
                        this.facingDirection = Direction.RIGHT;}
                    break;
            }
        }
    }

}
