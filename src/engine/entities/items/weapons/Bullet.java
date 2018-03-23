package engine.entities.items.weapons;

import engine.entities.GameObject;
import engine.entities.ScriptableObject;
import engine.entities.world.Tile;
import engine.controllers.Direction;
import engine.entities.composites.CollisionComponent;
import engine.entities.composites.Sprite;
import engine.controllers.ScriptableObjectController;

public class Bullet extends ScriptableObject {

    private int damage;
    private Direction direction;
    private boolean hasImpacted;
    private CollisionComponent collisionComponent;
    private GameObject gameObject;
    private double sysTime;

    public Bullet(ScriptableObjectController controller, int damage, Tile startTile, Direction direction) {
        super(controller);
        this.gameObject = new GameObject(setSpriteByDirection(direction));
        this.gameObject.getTransformComponent().setCurrentTile(startTile);
        startTile.setGameObject(gameObject);
        this.damage = damage;
        this.direction = direction;
        this.collisionComponent = new CollisionComponent();
        this.hasImpacted = false;
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
        if (!(this.sysTime + 50 > System.currentTimeMillis())){

            if (this.damage < 1){
                this.controller.addToBeDeletedList(this);
                this.gameObject.getTransformComponent().getCurrentTile().clearGameObject();
            }
            else if(!this.collisionComponent.collisionDetect(this.gameObject.getTransformComponent().getCurrentTile(), direction)){
                gameObject.getTransformComponent().move(direction);
                this.damage--;
            }
            else {
                gameObject.getTransformComponent().getCurrentTile().getTileInDirection(direction).getGameObject().hit(damage);
                gameObject.getTransformComponent().getCurrentTile().clearGameObject();
                controller.addToBeDeletedList(this);
                this.hasImpacted = true;
                this.damage--;
            }
            this.sysTime = System.currentTimeMillis();
        }
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
