package engine;

import engine.composites.GraphicsComponent;
import engine.composites.Sprite;
import engine.composites.TransformComponent;
import javafx.scene.control.Skin;


/**
 * Represents the abstract GameObject.
 * A GameObject subtype object can be on a Tile object.
 */
public class GameObject {
    private TransformComponent transformComponent;
    private GraphicsComponent graphicsComponent;

    public GameObject(TransformComponent tc, GraphicsComponent gc){
        this.transformComponent = tc;
        this.graphicsComponent = gc;
    }

    public GraphicsComponent getGraphicsComponent(){return graphicsComponent;}


    public TransformComponent getTransformComponent(){return transformComponent;}


    public void move(Direction direction){
        transformComponent.move(direction);
    }
    public void moveTowards(int playerPosX,int playerPosY){
        boolean x = this.getTile().cordX > playerPosX;
        boolean y = this.getTile().cordY > playerPosY;
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
}
