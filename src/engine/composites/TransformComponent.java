package engine.composites;

import engine.ActionEvent;
import engine.Direction;
import engine.Tile;

public class TransformComponent implements Component{

    private Tile currentTile;
    private Direction facingDirection;

    public Tile getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }

    public Direction getFacingDirection() {
        if (facingDirection == null){return Direction.UP;}
        return facingDirection;
    }

    public void move(Direction direction) {
        Tile lastTile = currentTile;
        switch (direction) {
            case UP:
                if(currentTile.getUp() != null){
                currentTile.getUp().setGameObject(currentTile.getGameObject());
                lastTile.clearTile();
                this.facingDirection = Direction.UP;}
                break;
            case DOWN:
                if(currentTile.getDown() != null){
                currentTile.getDown().setGameObject(currentTile.getGameObject());
                lastTile.clearTile();
                this.facingDirection = Direction.DOWN;}
                break;
            case LEFT:
                if(currentTile.getLeft() != null){
                currentTile.getLeft().setGameObject(currentTile.getGameObject());
                lastTile.clearTile();
                this.facingDirection = Direction.LEFT;}
                break;
            case RIGHT:
                if(currentTile.getRight() != null){
                    currentTile.getRight().setGameObject(currentTile.getGameObject());
                    lastTile.clearTile();
                    this.facingDirection = Direction.RIGHT;}
                break;
        }

    }
}
