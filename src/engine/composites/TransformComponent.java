package engine.composites;

import engine.ActionEvent;
import engine.Direction;
import engine.Tile;

public class TransformComponent implements Component{

    private Tile currentTile;

    public Tile getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }

    public void move(Direction direction) {
        Tile lastTile = currentTile;
        switch (direction) {
            case UP:
                currentTile.getUp().setGameObject(currentTile.getGameObject());
                lastTile.clearTile();
                break;
            case DOWN:
                currentTile.getDown().setGameObject(currentTile.getGameObject());
                lastTile.clearTile();
                break;
            case LEFT:
                currentTile.getLeft().setGameObject(currentTile.getGameObject());
                lastTile.clearTile();
                break;
            case RIGHT:
                currentTile.getRight().setGameObject(currentTile.getGameObject());
                lastTile.clearTile();
                break;
        }

    }
}
