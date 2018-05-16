package engine.entities.gameobjects;

import engine.controllers.GameHandler;
import engine.controllers.GameUpdater;
import engine.controllers.Updater;
import engine.entities.components.*;
import engine.entities.gameobjects.interfaces.GameObject;
import engine.entities.gameobjects.interfaces.IUpdatableGameObject;
import engine.entities.items.weapons.ZombieAttack;
import engine.world.Tile;

import java.util.ArrayList;
import java.util.List;


public class EnemyFactory {


    private static IUpdatableGameObject createEnemy(List<Sprite> spriteList, Updater gameUpdater, BasicEntityBlueprint blueprint, Tile spawnTile, GameObject player, GameHandler gameHandler){

        SingleWeaponComponent wc = new SingleWeaponComponent();
        KillableHealthComponent hc = new KillableHealthComponent(blueprint.health);
        UpdatableTransformComponent tc = new UpdatableTransformComponent(spawnTile);
        EnemyInputComponent ic = new EnemyInputComponent(player, gameHandler, blueprint.moveDelay);
        GameObjectCollisionComponent cc = new GameObjectCollisionComponent();
        UpdatableGraphicsComponent gc = new UpdatableGraphicsComponent(spriteList.get(0), blueprint.moveDelay);

        for (Sprite sprite :
                spriteList) {
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

    public static IUpdatableGameObject createEnemy(EnemyType type, GameHandler gameHandler, Tile spawnTile, BasicEntityBlueprint blueprint){
        /*
        Setting the sprites for a enemy, the numbering is not intuitive
        */
        List<Sprite> zombieSprites = new ArrayList<>();
        List<Sprite> batSprites = new ArrayList<>();
        zombieSprites.add(new Sprite(18)); //down
        zombieSprites.add(new Sprite(19)); //up
        zombieSprites.add(new Sprite(20)); //left
        zombieSprites.add(new Sprite(21)); //right

        zombieSprites.add(new Sprite(23)); //up
        zombieSprites.add(new Sprite(22)); //down
        zombieSprites.add(new Sprite(24)); //right
        zombieSprites.add(new Sprite(25)); //left

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


        if(type == EnemyType.ZOMBIE){
            return createEnemy(zombieSprites, gameHandler.getGameUpdater(), blueprint, spawnTile, gameHandler.getPlayer(), gameHandler);
        }
        else if (type == EnemyType.BAT) {
            return createEnemy(batSprites, gameHandler.getGameUpdater(), blueprint, spawnTile, gameHandler.getPlayer(), gameHandler);
        }
        else {
            return null;
        }
    }


    public enum EnemyType{
        ZOMBIE, BAT
    }
}
