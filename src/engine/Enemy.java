package engine;

import engine.composites.GraphicsComponent;
import engine.composites.Sprite;
import engine.composites.TransformComponent;

public class Enemy extends GameObject {
    public Enemy(TransformComponent tc, GraphicsComponent gc) {
        super(tc, gc);
    }

}



