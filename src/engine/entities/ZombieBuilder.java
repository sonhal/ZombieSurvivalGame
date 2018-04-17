package engine.entities;

import engine.entities.composites.*;
import engine.entities.interfaces.IUpdatableGameObject;
import engine.entities.items.weapons.MeleeWeapon;
import engine.entities.world.Tile;

import java.util.ArrayList;

public class ZombieBuilder {

    public static UpdatableGameObject createZombie(IUpdatableGameObject player, Tile spawnTile){
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
        HealthComponent hc = new HealthComponent(100);
        AvatarTransformComponent tc = new AvatarTransformComponent(spawnTile);
        EnemyInputComponent ic = new EnemyInputComponent(player, 1000);

        AvatarGraphicsComponent gc = new AvatarGraphicsComponent(zombieSprites.get(0), 500);

        for (Sprite sprite:
                zombieSprites) {
            gc.addSprite(sprite);
        }

        wc.setWeapon((new MeleeWeapon(1,5,1000)));

        ArrayList<ScriptableComponent> components = new ArrayList<>();
        components.add(gc);
        components.add(wc);
        components.add(cc);
        components.add(hc);
        components.add(tc);
        components.add(ic);

        return new ImpUpdatableGameObject(components);
    }
}
