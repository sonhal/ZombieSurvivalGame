package engine.entities;

import engine.entities.composites.*;

import java.util.ArrayList;

public class AvatarFactory {


    public static Avatar create(ArrayList<Sprite> sprites, int health){
        GraphicsComponent gc = new GraphicsComponent(sprites.get(0));

        for (Sprite sprite:
             sprites) {
            gc.addSprite(sprite);
        }

        WeaponComponent ac = new WeaponComponent();
        CollisionComponent cc = new CollisionComponent();
        HealthComponent hc = new HealthComponent(health);
        return new Avatar(gc, hc, ac, cc);
    }
}
