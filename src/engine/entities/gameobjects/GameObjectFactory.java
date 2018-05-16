package engine.entities.gameobjects;

import engine.controllers.Direction;
import engine.controllers.Updater;
import engine.entities.components.*;
import engine.entities.gameobjects.interfaces.GameObject;
import engine.entities.gameobjects.interfaces.IUpdatableGameObject;
import engine.world.Tile;

import java.util.ArrayList;
import java.util.Collections;

/**
 * GameObject factory for common GameObjects that are instantiated often during game play
 */
public class GameObjectFactory {

    public static GameObject createStaticGameObject(Sprite sprite, Tile startTile){

        return new StaticGameObject.Builder(new StaticTransformComponent(startTile))
                .addComponent(new StaticGraphicsComponent(sprite))
                .build();
    }

    public static IUpdatableGameObject createBullet(Tile startTile, Direction direction, int damage){
        return new UpdatableGameObject.Builder(new UpdatableTransformComponent(startTile))
                .addComponent(new StaticGraphicsComponent(getBulletSpriteByDirection(direction)))
                .addComponent(new SingleAttackComponent(damage))
                .addComponent(new GameObjectCollisionComponent())
                .addComponent(new ProjectileInputComponent(direction, 10))
                .addComponent(new ProjectileHealthComponent(20))
                .build();
    }

    public static IUpdatableGameObject explodingBullet(Tile startTile, Direction direction, int damage, Updater updater){
        return new UpdatableGameObject.Builder(new UpdatableTransformComponent(startTile))
                .addComponent(new StaticGraphicsComponent(getBulletSpriteByDirection(direction)))
                .addComponent(new SingleAttackComponent(damage))
                .addComponent(new GameObjectCollisionComponent())
                .addComponent(new ProjectileInputComponent(direction, 10))
                .addComponent(new ProjectileHealthComponent(20))
                .addComponent(new OnDeathComponent(updater))
                .build();
    }

    public static IUpdatableGameObject ShootGunBullet(Tile startTile, Direction direction, int damage, Updater updater, boolean haveSpread, int range){
        return new UpdatableGameObject.Builder(new ShotGunUpdatableTransformComponent(startTile, 3,haveSpread, updater))
                .addComponent(new StaticGraphicsComponent(getBulletSpriteByDirection(direction)))
                .addComponent(new SingleAttackComponent(damage))
                .addComponent(new GameObjectCollisionComponent())
                .addComponent(new ProjectileInputComponent(direction, 10))
                .addComponent(new ProjectileHealthComponent(range))
                .addComponent(new OnDeathComponent(updater))
                .build();
    }

    public static IUpdatableGameObject knifeAttackParticleEffect(Tile tile, Direction direction, int damage){
        ArrayList<Sprite> sprites = new ArrayList<>();
        Collections.addAll(sprites, getSwordSpriteByDirection(direction));
        return new UpdatableGameObject.Builder(new ParticleTransformComponent(tile))
                .addComponent(new OneUseUpdatableGraphicsComponent(sprites,50))
                .build();
    }

    public static IUpdatableGameObject batAnimationParticleEffect(Tile tile, Direction direction, int damage){
        ArrayList<Sprite> sprites = new ArrayList<>();
        Collections.addAll(sprites, getBatSpriteByDirection(direction));
        return new UpdatableGameObject.Builder(new ParticleTransformComponent(tile))
                .addComponent(new OneUseUpdatableGraphicsComponent(sprites,50))
                .build();
    }


    public static IUpdatableGameObject explosion(Tile tile, Direction direction, int damage){
        ArrayList<Sprite> sprites = new ArrayList<>();
        sprites.add(new Sprite(30));
        sprites.add(new Sprite(31));
        sprites.add(new Sprite(32));
        sprites.add(new Sprite(33));
        sprites.add(new Sprite(34));
        sprites.add(new Sprite(35));

        return new UpdatableGameObject.Builder(new ParticleTransformComponent(tile))
                .addComponent(new OneUseUpdatableGraphicsComponent(sprites,100))
                .build();
    }

    protected static Sprite getBulletSpriteByDirection(Direction direction){
            switch (direction){
                case UP:
                    return new Sprite(8);
                case DOWN:
                    return new Sprite(9);
                case LEFT:
                    return new Sprite(10);
                case RIGHT:
                    return new Sprite(11);
                default:
                    return new Sprite(8);
        }
    }

    protected static Sprite[] getSwordSpriteByDirection(Direction direction){
        switch (direction){
            case UP:
                return new Sprite[]{new Sprite(37), new Sprite(38), new Sprite(39)};
            case DOWN:
                return new Sprite[]{new Sprite(40), new Sprite(41), new Sprite(42)};
            case LEFT:
                return new Sprite[]{new Sprite(43), new Sprite(44), new Sprite(45)};
            case RIGHT:
                return new Sprite[]{new Sprite(46), new Sprite(47), new Sprite(48)};
            default:
                return new Sprite[]{new Sprite(37), new Sprite(38), new Sprite(39)};
        }
    }

    protected static Sprite[] getBatSpriteByDirection(Direction direction){
        switch (direction){
            case UP:
                return new Sprite[]{new Sprite(54), new Sprite(55), new Sprite(56)};
            case DOWN:
                return new Sprite[]{new Sprite(57), new Sprite(58), new Sprite(59)};
            case LEFT:
                return new Sprite[]{new Sprite(60), new Sprite(61), new Sprite(62)};
            case RIGHT:
                return new Sprite[]{new Sprite(63), new Sprite(64), new Sprite(65)};
            default:
                return new Sprite[]{new Sprite(54), new Sprite(55), new Sprite(56)};
        }
    }
}
