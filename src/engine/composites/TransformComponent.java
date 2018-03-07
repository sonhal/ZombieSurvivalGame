package engine.composites;

import engine.ActionEvent;
import engine.Direction;
import engine.Tile;

public class TransformComponent implements Component{

    private Tile currentTile;

    public TransformComponent(Tile currentTile){
        this.currentTile = currentTile;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }

    public void move(Direction direction) {
        switch (direction) {
            case UP:
                currentTile = currentTile.getUp();
            case DOWN:
                currentTile = currentTile.getDown();
            case LEFT:
                currentTile = currentTile.getLeft();
            case RIGHT:
                currentTile = currentTile.getRight();
        }

    }
}
