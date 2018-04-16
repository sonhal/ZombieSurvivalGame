package engine.entities.composites;

import engine.controllers.Direction;
import engine.entities.interfaces.IGameObject;
import engine.entities.world.Tile;

import java.io.Serializable;

public class TransformComponent extends ScriptableComponent{

    private IGameObject gameObject;
    private Tile currentTile;
    private Direction facingDirection;
    protected Direction move;

    public TransformComponent(){
        super(ComponentType.TRANSFORM_COMPONENT);
    }


    @Override
    public void update(IGameObject componentHolder) {
        //Does nothing
    }

    @Override
    public void handle(Message message) {
        if(message.message instanceof Direction){
            move = (Direction) message.message;
        }
    }

    @Override
    public void innit(IGameObject gameObject) {
        if (gameObject == null){
            throw new IllegalStateException("GameObject is null");
        }
        this.gameObject = gameObject;
    }


    public Tile getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(Tile currentTile) {

        this.currentTile = currentTile;
        this.currentTile.setGameObject(gameObject);
    }

    public Direction getFacingDirection() {
        if (facingDirection == null){return Direction.UP;}
        return facingDirection;
    }

    public void setFacingDirection(Direction facingDirection) {
        this.facingDirection = facingDirection;
    }

    public void move(Direction direction) {
        if (currentTile != null){
            Tile lastTile = currentTile;
            Tile newTile = currentTile.getTileInDirection(direction);
            switch (direction) {
                case UP:
                    if(currentTile.getUp() != null){
                        lastTile.clearGameObject();
                        setCurrentTile(newTile);
                        this.facingDirection = Direction.UP;}
                    break;
                case DOWN:
                    if(currentTile.getDown() != null){
                        lastTile.clearGameObject();
                        setCurrentTile(newTile);
                        this.facingDirection = Direction.DOWN;}
                    break;
                case LEFT:
                    if(currentTile.getLeft() != null){
                        lastTile.clearGameObject();
                        setCurrentTile(newTile);
                        this.facingDirection = Direction.LEFT;}
                    break;
                case RIGHT:
                    if(currentTile.getRight() != null){
                        lastTile.clearTile();
                        setCurrentTile(newTile);
                        this.facingDirection = Direction.RIGHT;}
                    break;
            }
        }
    }

    public void setGameObject(IGameObject gameObject){
        this.gameObject = gameObject;
    }
}
