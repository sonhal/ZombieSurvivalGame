package engine;

import engine.composites.GraphicsComponent;
import engine.composites.Sprite;
import engine.composites.TransformComponent;

public class Gun extends Weapon {
    /**
     * Sets the damage for the weapon
     *
     * @param range
     * @param damage the damage the weapon deals
     */
    public Gun(int range, int damage) {
        super(range, damage);
    }

    @Override
    public void activate(Tile fromTile, Direction direction) {
        Tile startTile = null;

        switch (direction){
            case UP:
                startTile = fromTile.getUp();
                break;
            case DOWN:
                startTile = fromTile.getDown();
                break;
            case LEFT:
                startTile = fromTile.getLeft();
                break;
            case RIGHT:
                startTile = fromTile.getRight();
                break;
        }
        Bullet bullet = new Bullet(new TransformComponent(), new GraphicsComponent(new Sprite(10)), 1, startTile, direction);
        bullet.getTransformComponent().setCurrentTile(fromTile);
    }
}
