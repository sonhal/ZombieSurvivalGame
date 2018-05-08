package engine.entities.components;

import engine.controllers.Direction;
import engine.entities.components.ComponentEvent.ComponentEvent;
import engine.entities.components.ComponentEvent.DeathEvent;
import engine.entities.components.ComponentEvent.MoveEvent;
import engine.entities.gameobjects.interfaces.IGameObject;
import engine.world.Tile;

public class TransformComponent extends ScriptableComponent{

    private Tile currentTile;
    private Direction facingDirection;
    protected Direction move;
    protected boolean removeSelf;

    public TransformComponent(Tile connectedTile){
        super(ComponentType.TRANSFORM_COMPONENT);
        currentTile = connectedTile;
    }


    @Override
    public void update(IGameObject gameObject) {
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
    public void innit(IGameObject gameObject) {
        this.currentTile.setGameObject(gameObject);
    }

    @Override
    public void cleanUp(IGameObject gameObject) {
        if(currentTile != null){
            getCurrentTile().clearGameObject();
            currentTile = null;
        }
    }


    public Tile getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(Tile tile) {
        this.currentTile = tile;
    }

    public Direction getFacingDirection() {
        if (facingDirection == null){return Direction.UP;}
        return facingDirection;
    }

    public void setFacingDirection(Direction facingDirection) {
        this.facingDirection = facingDirection;
    }

    private void switchTilePlacement(Tile oldTile, Tile newTile, IGameObject gameObject){
        oldTile.clearGameObject();
        setCurrentTile(newTile);
        currentTile.setGameObject(gameObject);
    }

    public void move(Direction direction, IGameObject gameObject) {
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
