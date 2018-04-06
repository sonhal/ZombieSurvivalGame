package engine.entities.items.weapons;

import engine.entities.GameObject;
import engine.entities.Hittable;
import engine.entities.ScriptableObject;
import engine.entities.composites.TimeComponent;
import engine.entities.world.Tile;
import engine.controllers.Direction;
import engine.entities.composites.CollisionComponent;
import engine.entities.composites.Sprite;

/**
 * Object meant to be instantiated by a Gun object.
 * Creates a gameObject representing a bullet fired in the game and controls the gameObjects travel and interactions.
 * Travels across the game world to the range is meet or it hits a GameObject
 */
public class Bullet extends ScriptableObject {

    private int damage;
    private Direction direction;
    private CollisionComponent collisionComponent;
    private GameObject gameObject;
    private double sysTime;
    private int range;
    private int speed;

    public Bullet(int range, int damage, int speed, Tile startTile, Direction direction) {
        this.speed = speed;
        this.range = range;
        this.gameObject = new GameObject(setSpriteByDirection(direction));
        this.gameObject.getTransformComponent().setCurrentTile(startTile);
        startTile.setGameObject(gameObject);
        this.damage = damage;
        this.direction = direction;
        this.collisionComponent = new CollisionComponent();
        this.sysTime = System.currentTimeMillis();
    }

    @Override
    public void start() {
        update();
    }

    @Override
    public void stop() {
        System.out.println(this.toString() + " is stopping");
    }

    @Override
    public void update(){
        if (TimeComponent.canUpdate(speed, sysTime)){
            if (this.range < 1){
                removeSelfFromGame();
            }
            else if(!this.collisionComponent.collisionDetect(this.gameObject.getTransformComponent().getCurrentTile(), direction)){
                gameObject.getTransformComponent().move(direction);
                this.range--;
            }
            else {
                hitGameObject();
                removeSelfFromGame();
            }
            this.sysTime = System.currentTimeMillis();
        }
    }

    private void hitGameObject(){
        GameObject objectToBeAttacked =
                gameObject.getTransformComponent().getCurrentTile().getTileInDirection(direction).getGameObject();
        if( objectToBeAttacked instanceof Hittable){
            ((Hittable)objectToBeAttacked).hit(damage);
        }
    }

    private void removeSelfFromGame(){
        gameObject.getTransformComponent().getCurrentTile().clearGameObject();
        die();
    }

    protected Sprite setSpriteByDirection(Direction direction){
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

}
