package engine;

import engine.composites.*;

import java.util.ArrayList;

public class AvatarFactory {


    public static Avatar create(ArrayList<Sprite> sprites, int health){
        TransformComponent tc = new TransformComponent();
        GraphicsComponent gc = new GraphicsComponent(sprites.get(0));

        for (Sprite sprite:
             sprites) {
            gc.addSprite(sprite);
        }

        AttackComponent ac = new AttackComponent(tc);
        CollisionComponent cc = new CollisionComponent();
        HealthComponent hc = new HealthComponent(health);
        return new Avatar(tc,gc, hc, ac, cc);
    }
}
