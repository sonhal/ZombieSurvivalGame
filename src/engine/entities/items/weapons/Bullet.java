package engine.entities.items.weapons;

import engine.entities.*;
import engine.entities.composites.*;
import engine.entities.interfaces.Collidable;
import engine.entities.interfaces.Hittable;
import engine.entities.interfaces.IGameObject;
import engine.entities.interfaces.Updatable;
import engine.entities.world.Tile;
import engine.controllers.Direction;

import java.io.Serializable;

/**
 * Object meant to be instantiated by a Gun object.
 * Creates a gameObject representing a bullet fired in the game and controls the gameObjects travel and interactions.
 * Travels across the game world to the range is meet or it hits a GameObject
 */
public class Bullet extends GameObject implements Collidable, Serializable, Updatable {

    private int damage;
    private Direction direction;
    private CollisionComponent collisionComponent;
    private InputComponent inputComponent;
    private double sysTime;
    private int range;
    private int speed;
    private boolean isDead;

    public Bullet(GraphicsComponent gc, TransformComponent tc, int range, int damage, int speed, Tile startTile, Direction direction) {
        super(tc, gc);
        this.speed = speed;
        this.range = range;
        this.damage = damage;
        this.direction = direction;
        this.collisionComponent = new CollisionComponent();
        tc.setGameObject(this);
        tc.setCurrentTile(startTile);
        this.sysTime = System.currentTimeMillis();
        this.inputComponent = new InputComponent() {
            @Override
            public void update(Avatar avatar) {
                //do nothing
            }

            @Override
            public Direction getMoveEvent() {
                return direction;
            }

            @Override
            public Direction getAttackEvent() {
                return null;
            }
        };
    }

    @Override
    public void update(){
        this.collisionComponent.update(this);
        if (TimeComponent.canUpdate(speed, sysTime)){
            if (this.range < 1){
                removeSelfFromGame();
            }
            else if(this.collisionComponent.collided() != null){
                hitGameObject();
                removeSelfFromGame();
            }
            else {
                getTransformComponent().move(direction);
                this.range--;
            }
            this.sysTime = System.currentTimeMillis();
        }
    }

    @Override
    public boolean isDead() {
        return isDead;
    }

    private void hitGameObject(){
        IGameObject objectToBeAttacked =
                getTransformComponent().getCurrentTile().getTileInDirection(direction).getGameObject();
        if( objectToBeAttacked instanceof Hittable){
            ((Hittable)objectToBeAttacked).hit(damage);
        }
    }

    private void removeSelfFromGame(){
        getTransformComponent().getCurrentTile().clearGameObject();
        isDead = true;
    }

    protected static Sprite getSpriteByDirection(Direction direction){
        switch (direction){
            case UP:
                return new Sprite(10);
            case DOWN:
                return new Sprite(11);
            case LEFT:
                return new Sprite(8);
            case RIGHT:
                return new Sprite(9);
            default:
                return new Sprite(8);
        }
    }

    @Override
    public InputComponent getInputComponent() {
        return inputComponent;
    }

}
