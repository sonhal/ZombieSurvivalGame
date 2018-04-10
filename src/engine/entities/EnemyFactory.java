package engine.entities;

import engine.entities.composites.*;
import engine.entities.items.weapons.MeleeWeapon;

import java.util.ArrayList;

public class EnemyFactory {

    public static Avatar createZombie(Avatar player){
        ArrayList<Sprite> zombieSprites = new ArrayList<>();

        /*
        Setting the sprites for a enemy, the numbering is not intuitive
        */
        zombieSprites.add(new Sprite(18)); //down
        zombieSprites.add(new Sprite(19)); //up
        zombieSprites.add(new Sprite(20)); //left
        zombieSprites.add(new Sprite(21)); //right

        zombieSprites.add(new Sprite(23)); //up
        zombieSprites.add(new Sprite(22)); //down
        zombieSprites.add(new Sprite(24)); //right
        zombieSprites.add(new Sprite(25)); //left

        WeaponComponent wc = new WeaponComponent();
        CollisionComponent cc = new CollisionComponent();
        HealthComponent hc = new HealthComponent(30);
        AvatarTransformComponent tc = new AvatarTransformComponent(500,1000);
        EnemyInputComponent ic = new EnemyInputComponent(player, tc);

        AvatarGraphicsComponent gc = new AvatarGraphicsComponent(zombieSprites.get(0));

        for (Sprite sprite:
                zombieSprites) {
            gc.addSprite(sprite);
        }
        Avatar zombie = new Avatar(gc, hc, wc, cc,ic, tc);


        zombie.pickupWeapon(new MeleeWeapon(1,5,1000));
        return zombie;
    }
}
