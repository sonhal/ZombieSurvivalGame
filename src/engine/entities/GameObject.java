package engine.entities;

import engine.entities.world.Tile;
import engine.controllers.Direction;
import engine.entities.composites.GraphicsComponent;
import engine.entities.composites.Sprite;
import engine.entities.composites.TransformComponent;


/**
 * Represents the abstract GameObject.
 * A GameObject subtype object can be on a Tile object.
 */
public class GameObject {
    private TransformComponent transformComponent;
    private GraphicsComponent graphicsComponent;
    private boolean isHit = false;

    public GameObject(TransformComponent tc, GraphicsComponent gc){
        this.transformComponent = tc;
        this.graphicsComponent = gc;
    }

    public GameObject(GraphicsComponent gc){
        this.graphicsComponent = gc;
        this.transformComponent = new TransformComponent(this);
    }

    public GameObject(Sprite sprite){
        this.transformComponent = new TransformComponent(this);
        this.graphicsComponent = new GraphicsComponent(sprite);
    }

    public GameObject(){
        this(new Sprite(2));
    }

    public GraphicsComponent getGraphicsComponent(){return graphicsComponent;}


    public TransformComponent getTransformComponent(){return transformComponent;}


    public void move(Direction direction){
        transformComponent.move(direction);
    }

    public void moveTowards(int playerPosX,int playerPosY){
        boolean x = this.getTile().getCordX() > playerPosX;
        boolean y = this.getTile().getCordY() > playerPosY;
        if (x){
            if (y){
                if (Math.random() < 0.5) { this.move(Direction.UP);} else{this.move(Direction.RIGHT);}
            }else {
                if (Math.random() < 0.5) { this.move(Direction.DOWN);} else{this.move(Direction.RIGHT);}
            }
        }else {
            if (y){
                if (Math.random() < 0.5) { this.move(Direction.UP);} else{this.move(Direction.LEFT);}
            }else {
                if (Math.random() < 0.5) { this.move(Direction.DOWN);} else{this.move(Direction.LEFT);}
            }
        }
    }
    
    public Sprite getSprite(){
        return graphicsComponent.getSprite();
    }

    public Tile getTile(){ return transformComponent.getCurrentTile();}

    public void hit(int hitAmount){
        setIsHit(true);
        System.out.println("Object was hit with :" + hitAmount);
    }

    public boolean isHit(){
        return isHit;
    }

    public void setIsHit(boolean isHit){
        this.isHit = isHit;
    }
}
