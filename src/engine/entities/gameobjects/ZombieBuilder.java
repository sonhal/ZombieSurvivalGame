package engine.entities.gameobjects;

import engine.entities.components.*;
import engine.entities.gameobjects.interfaces.IUpdatableGameObject;
import engine.entities.items.weapons.MeleeWeapon;
import engine.world.Tile;

import java.util.ArrayList;

/**
 * Helper class, Builds new Zombies
 */
public class ZombieBuilder {

    public static UpdatableGameObject createZombie(IUpdatableGameObject player,
                                                   Tile spawnTile, BasicEntityBlueprint blueprint){
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

        SingleWeaponComponent wc = new SingleWeaponComponent();
        KillableHealthComponent hc = new KillableHealthComponent(blueprint.health);
        UpdatableTransformComponent tc = new UpdatableTransformComponent(spawnTile);
        EnemyInputComponent ic = new EnemyInputComponent(player, blueprint.moveDelay);
        GameObjectCollisionComponent cc = new GameObjectCollisionComponent();
        UpdatableGraphicsComponent gc = new UpdatableGraphicsComponent(zombieSprites.get(0), blueprint.moveDelay);

        for (Sprite sprite:
                zombieSprites) {
            gc.addSprite(sprite);
        }

        wc.setWeapon(new MeleeWeapon(100 ,new SingleAttackComponent(blueprint.attackDamage)));

        return new UpdatableGameObject.Builder(tc)
                .addComponent(gc)
                .addComponent(wc)
                .addComponent(cc)
                .addComponent(hc)
                .addComponent(ic)
                .addComponent(new SoundEffectComponent(500, blueprint.attackSound))
                .build();
    }
}
