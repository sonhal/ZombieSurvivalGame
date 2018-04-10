package engine.entities;

import engine.entities.interfaces.IGameObject;
import engine.entities.world.Tile;
import engine.entities.composites.GraphicsComponent;
import engine.entities.composites.Sprite;
import engine.entities.composites.TransformComponent;

import java.io.Serializable;


/**
 * Represents the abstract GameObject.
 * A GameObject subtype object can be on a Tile object.
 */
public class GameObject implements IGameObject,Serializable{
    private TransformComponent transformComponent;
    private GraphicsComponent graphicsComponent;

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
    
    public Sprite getSprite(){
        return graphicsComponent.getSprite();
    }

    public Tile getTile(){ return transformComponent.getCurrentTile();}

}
