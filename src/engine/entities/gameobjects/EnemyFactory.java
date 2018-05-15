package engine.entities.gameobjects;

import engine.controllers.GameHandler;
import engine.entities.components.*;
import engine.entities.gameobjects.interfaces.IUpdatableGameObject;
import engine.entities.items.weapons.ZombieAttack;
import engine.world.Tile;

import java.util.ArrayList;

public class EnemyFactory {

    public static UpdatableGameObject createBatEnemy(IUpdatableGameObject player,
                                                     Tile spawnTile, BasicEntityBlueprint blueprint, GameHandler gameHandler) {
        ArrayList<Sprite> batSprites = new ArrayList<>();

        /*
        Setting the sprites for a enemy, the numbering is not intuitive
        */
        batSprites.add(new Sprite(54)); //up1
        batSprites.add(new Sprite(55)); //up2
        batSprites.add(new Sprite(56)); //up3
        batSprites.add(new Sprite(57)); //down1
        batSprites.add(new Sprite(58)); //down2
        batSprites.add(new Sprite(59)); //down3
        batSprites.add(new Sprite(60)); //left1
        batSprites.add(new Sprite(61)); //left2
        batSprites.add(new Sprite(62)); //left3
        batSprites.add(new Sprite(63)); //right1
        batSprites.add(new Sprite(64)); //right2
        batSprites.add(new Sprite(65)); //right3


        SingleWeaponComponent wc = new SingleWeaponComponent();
        KillableHealthComponent hc = new KillableHealthComponent(blueprint.health);
        UpdatableTransformComponent tc = new UpdatableTransformComponent(spawnTile);
        EnemyInputComponent ic = new EnemyInputComponent(player, gameHandler, blueprint.moveDelay);
        GameObjectCollisionComponent cc = new GameObjectCollisionComponent();
        UpdatableGraphicsComponent gc = new UpdatableGraphicsComponent(batSprites.get(0), blueprint.moveDelay);

        for (Sprite sprite :
                batSprites) {
            gc.addSprite(sprite);
        }

        wc.setActiveWeapon(new ZombieAttack(new SoundEffectComponent(500, blueprint.attackSound), new SingleAttackComponent(blueprint.attackDamage), blueprint.moveDelay));

        return new UpdatableGameObject.Builder(tc)
                .addComponent(gc)
                .addComponent(wc)
                .addComponent(cc)
                .addComponent(hc)
                .addComponent(ic)
                .addComponent(new LootDropComponent())
                .addComponent(wc)
                .build();
    }
}
