package engine.entities.gameobjects;

import engine.controllers.Direction;
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
}
