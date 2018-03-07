package engine;

import engine.composites.GraphicsComponent;
import engine.composites.Sprite;
import engine.composites.TransformComponent;
import javafx.scene.control.Skin;


/**
 * Represents the abstract GameObject.
 * A GameObject subtype object can be on a Tile object.
 */
public abstract class GameObject {
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

    public Sprite getSprite(){
        return graphicsComponent.getSprite();
    }
}
