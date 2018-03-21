package engine;

import engine.composites.GraphicsComponent;
import engine.composites.Sprite;
import engine.composites.TransformComponent;

public class Gun extends Weapon {

    private GameHandler gameHandler;

    public Gun(GameHandler gameHandler, int range, int damage) {
        super(range, damage);
        this.gameHandler = gameHandler;
    }

    @Override
    public void activate( Tile fromTile, Direction direction) {
        System.out.println("Weapon activated!");
        Tile startTile = fromTile.getTileInDirection(direction);
        gameHandler.addToUpdateList(new Bullet(gameHandler,damage, startTile, direction));
    }
}
