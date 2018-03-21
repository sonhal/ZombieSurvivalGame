package engine.composites;

import engine.Direction;
import engine.Tile;

public class CollisionComponent {

    private Direction collided;

    public boolean collisionDetect(Tile currentTile, Direction direction){

        boolean collision = false;
        switch (direction){
            case RIGHT:
                if(currentTile.getRight().getGameObject() != null){ collision = true; collided = Direction.RIGHT;}
                break;
            case LEFT:
                if(currentTile.getLeft().getGameObject() != null){ collision = true; collided = Direction.LEFT;}
                break;
            case UP:
                if(currentTile.getUp().getGameObject() != null){ collision = true; collided = Direction.UP;}
                break;
            case DOWN:
                if(currentTile.getDown().getGameObject() != null){ collision = true; collided = Direction.DOWN;}
                break;
            default: collided = null;
        }
        return collision;
    }

    public Direction collided(){
        return collided;
    }
}
