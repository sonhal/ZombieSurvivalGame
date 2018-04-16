package engine.entities.composites;

import engine.controllers.Direction;
import engine.entities.interfaces.Collidable;
import engine.entities.world.Tile;

import java.io.Serializable;

public class CollisionComponent implements Component<Collidable>, Serializable{

    private Direction collided;

    private void collisionDetect(Tile currentTile, Direction direction){

        switch (direction){
            case RIGHT:
                if(currentTile.getRight().getGameObject() != null){collided = Direction.RIGHT;}
                break;
            case LEFT:
                if(currentTile.getLeft().getGameObject() != null){ collided = Direction.LEFT;}
                break;
            case UP:
                if(currentTile.getUp().getGameObject() != null){ collided = Direction.UP;}
                break;
            case DOWN:
                if(currentTile.getDown().getGameObject() != null){ collided = Direction.DOWN;}
                break;
            default: collided = null;
        }
    }

    public Direction collided(){
        return collided;
    }

    public void clearCollided(){ collided = null; }

    @Override
    public void update(Collidable componentHolder) {
        if(componentHolder.getInputComponent().getMoveEvent() != null){
            clearCollided();
            collisionDetect(componentHolder.getTransformComponent().getCurrentTile(),
                    componentHolder.getInputComponent().getMoveEvent());
        }
    }
}
