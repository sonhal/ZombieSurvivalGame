package engine.entities.gameobjects;

import engine.controllers.Direction;
import engine.entities.components.*;
import engine.entities.gameobjects.interfaces.IGameObject;
import engine.entities.gameobjects.interfaces.IUpdatableGameObject;
import engine.world.Tile;

import java.util.ArrayList;

public class GameObjectFactory {

    public static IGameObject createStaticGameObject(Sprite sprite, Tile startTile){
        ArrayList<ScriptableComponent> components = new ArrayList<>();

        components.add(new TransformComponent(startTile));
        components.add(new GraphicsComponent(sprite));
        return new GameObject(components);
    }

    public static IUpdatableGameObject createBullet(Tile startTile, Direction direction, int damage){
        ArrayList<ScriptableComponent> components = new ArrayList<>();

        UpdatableTransformComponent at = new UpdatableTransformComponent(startTile);
        components.add(at);
        components.add(new GraphicsComponent(getBulletSpriteByDirection(direction)));
        components.add(new AttackComponent(damage));
        components.add(new CollisionComponent());
        components.add(new ProjectileInputComponent(direction, 10));
        components.add(new ProjectileHealthComponent(20));
        return new ImpUpdatableGameObject(components);
    }

    protected static Sprite getBulletSpriteByDirection(Direction direction){
        switch (direction){
            case UP:
                return new Sprite(10);
            case DOWN:
                return new Sprite(11);
            case LEFT:
                return new Sprite(8);
            case RIGHT:
                return new Sprite(9);
            default:
                return new Sprite(8);
        }
    }
}
