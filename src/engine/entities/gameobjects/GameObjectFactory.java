package engine.entities.gameobjects;

import engine.controllers.Direction;
import engine.controllers.Updater;
import engine.entities.components.*;
import engine.entities.gameobjects.interfaces.GameObject;
import engine.entities.gameobjects.interfaces.IUpdatableGameObject;
import engine.world.Tile;

import java.util.ArrayList;

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

    public static IUpdatableGameObject sword(Tile tile, Direction direction, int damage){
        ArrayList<Sprite> sprites = new ArrayList<>();
        sprites.add(new Sprite(37));
        sprites.add(new Sprite(40));
        sprites.add(new Sprite(43));
        sprites.add(new Sprite(46));

        return new UpdatableGameObject.Builder(new ParticleTransformComponent(tile))
                .addComponent(new OneUseUpdatableGraphicsComponent(sprites,100))
                .build();
    }

    public static IUpdatableGameObject swordFlay(Tile startTile, Direction direction, int damage, Updater updater){
        return new UpdatableGameObject.Builder(new UpdatableTransformComponent(startTile))
                .addComponent(new StaticGraphicsComponent(getSwordSpriteByDirection(direction)))
                .addComponent(new SingleAttackComponent(damage))
                .addComponent(new GameObjectCollisionComponent())
                .addComponent(new ProjectileInputComponent(direction, 10))
                .addComponent(new ProjectileHealthComponent(1))
                .addComponent(new OnDeathComponent(updater))
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

    protected static Sprite getSwordSpriteByDirection(Direction direction){
        switch (direction){
            case UP:
                return new Sprite(37);
            case DOWN:
                return new Sprite(40);
            case LEFT:
                return new Sprite(43);
            case RIGHT:
                return new Sprite(46);
            default:
                return new Sprite(37);
        }
    }
}
