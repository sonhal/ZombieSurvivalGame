package engine;

import engine.composites.GraphicsComponent;
import engine.composites.Sprite;
import engine.composites.TransformComponent;

public class GameObjectFactory {


    public static GameObject create(Tile tile, Sprite sprite){
        TransformComponent tc = new TransformComponent(tile);
        GraphicsComponent gc = new GraphicsComponent(sprite);
        return  new GameObject(tc,gc);
    }


}
