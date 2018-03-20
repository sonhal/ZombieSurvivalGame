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
                currentTile.getUp().setGameObject(currentTile.getGameObject());
                lastTile.clearTile();
                this.facingDirection = Direction.UP;
                break;
            case DOWN:
                currentTile.getDown().setGameObject(currentTile.getGameObject());
                lastTile.clearTile();
                this.facingDirection = Direction.DOWN;
                break;
            case LEFT:
                currentTile.getLeft().setGameObject(currentTile.getGameObject());
                lastTile.clearTile();
                this.facingDirection = Direction.LEFT;
                break;
            case RIGHT:
                currentTile.getRight().setGameObject(currentTile.getGameObject());
                lastTile.clearTile();
                this.facingDirection = Direction.RIGHT;
                break;
        }

    }
}
