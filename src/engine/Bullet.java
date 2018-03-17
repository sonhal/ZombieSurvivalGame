package engine;

import engine.composites.GraphicsComponent;
import engine.composites.TransformComponent;

public class Bullet extends GameObject {

    private int damage;
    private Direction direction;
    private boolean hasImpacted;

    public Bullet(TransformComponent tc, GraphicsComponent gc, int damage, Tile startTile, Direction direction) {
        super(tc, gc);
        this.damage = damage;
        this.direction = direction;
        this.hasImpacted = false;
    }


    public int hit(){
        return damage;
    }

    public void update(){
        if(!collisionDetect(direction)){
            getTransformComponent().move(direction);
        }
        else {
            getTransformComponent().getCurrentTile().getTileInDirection(direction).getGameObject().hit(damage);
            getTransformComponent().getCurrentTile().setGameObject(null);
        }


    }



    private boolean collisionDetect(Direction direction){
        Tile ct;
        ct = getTransformComponent().getCurrentTile();

        boolean collision = false;
        switch (direction){
            case RIGHT:
                if(ct.getRight().getGameObject() != null){ collision = true;}
                break;
            case LEFT:
                if(ct.getLeft().getGameObject() != null){ collision = true;}
                break;
            case UP:
                if(ct.getUp().getGameObject() != null){ collision = true;}
                break;
            case DOWN:
                if(ct.getDown().getGameObject() != null){ collision = true;}
        }
        return collision;
    }
}
