package engine.composites;

import engine.Direction;
import engine.Tile;

public class CollisionComponent {


    public boolean collisionDetect(Tile currentTile, Direction direction){

        boolean collision = false;
        switch (direction){
            case RIGHT:
                if(currentTile.getRight().getGameObject() != null){ collision = true;}
                break;
            case LEFT:
                if(currentTile.getLeft().getGameObject() != null){ collision = true;}
                break;
            case UP:
                if(currentTile.getUp().getGameObject() != null){ collision = true;}
                break;
            case DOWN:
                if(currentTile.getDown().getGameObject() != null){ collision = true;}
        }
        return collision;
    }
}
