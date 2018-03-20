package engine;

import engine.composites.CollisionComponent;
import engine.composites.GraphicsComponent;
import engine.composites.Sprite;
import engine.composites.TransformComponent;

public class Bullet extends ScriptableObject {

    private int damage;
    private Direction direction;
    private boolean hasImpacted;
    private CollisionComponent collisionComponent;
    private GameObject gameObject;
    private double sysTime;

    public Bullet(GameHandler gameHandler, int damage, Tile startTile, Direction direction) {
        super(gameHandler);
        this.gameObject = GameObjectFactory.create(new Sprite(3));
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
                this.gameHandler.addToBeDeletedList(this);
            }
            else if(!this.collisionComponent.collisionDetect(this.gameObject.getTransformComponent().getCurrentTile(), direction)){
                gameObject.getTransformComponent().move(direction);
                this.damage--;
            }
            else {
                gameObject.getTransformComponent().getCurrentTile().getTileInDirection(direction).getGameObject().hit(damage);
                gameObject.getTransformComponent().getCurrentTile().setGameObject(null);
                this.hasImpacted = true;
                this.damage--;
            }
            this.sysTime = System.currentTimeMillis();
        }
    }

}
