package engine.entities.components;

import engine.controllers.Direction;
import engine.controllers.Updater;
import engine.entities.components.ComponentEvent.CollisionEvent;
import engine.entities.components.ComponentEvent.ComponentEvent;
import engine.entities.gameobjects.GameObjectFactory;
import engine.entities.gameobjects.interfaces.GameObject;
import engine.world.Tile;

/**
 * Special Transformable component for the Shotgun weapon which keeps track on how far the bullet has traveled.
 * When the bullet has traveled as certain distance it will trigger itself two times.
 * Causeing a new bullet on each side of the original bullet to spawn.
 */
public class ShotGunUpdatableTransformComponent extends UpdatableTransformComponent {

int spreadAfter;
int traveledDistanse;
boolean haveSpread;
Updater updater;

    /**
     * Constructor Class mapping parameters.
     * @param connectedTile The tile in front of the player.
     * @param spreadAfter Distance the bullet will have to successfully travel before the spread event to occur
     * @param haveSpread This variable is to prevent the recursion to go on forever.
     * @param updater Refference to the gameobject updater, so the new bullets can be added to the update queue.
     */
    public ShotGunUpdatableTransformComponent(Tile connectedTile, int spreadAfter, boolean haveSpread, Updater updater) {
        super(connectedTile);
        this.spreadAfter = spreadAfter;
        this.updater = updater;
        this.haveSpread = haveSpread;
    }

    /**
     * Moves bullet to next tile in direction
     * @param direction Direction enum
     * @param gameObject The gameobject which is getting moved.
     */
    public void move(Direction direction, GameObject gameObject){
            super.move(direction, gameObject);
            setFacingDirection(direction);
    }

    /**
     * Moving the gameobject, and checks if the shotgun bullet should spread.
     * @param gameObject This bullet object.
     */
    @Override
    public void update (GameObject gameObject){
            if(move != null){
                setFacingDirection(move);
                if(move != collision){
                    move(move, gameObject);
                }
                move = null;
            }
            traveledDistanse++;
            if (traveledDistanse > spreadAfter && !haveSpread){
                spread();
                haveSpread = true;
            }
    }

    /**
     * Spawns one new bullet on each side of the existing bullet, facing in the same direction and moving just as the origional.
     * 1. Checks if the tiles on each side is empty, otherwise we could end up overwriting a tree or some other gameobject.
     * 2. Tells the updater to add the two new bullet objects that have been spawned on each side of the origional bullet.
     */
    void spread(){
        if (getCurrentTile().getTileInDirection(relativeDirections(getFacingDirection())[1]).getGameObject() == null) {
            updater.addToUpdateList(GameObjectFactory.ShootGunBullet(getCurrentTile().getTileInDirection(relativeDirections(getFacingDirection())[1]), getFacingDirection(), 50, updater, true, 7 ));
        }
        if (getCurrentTile().getTileInDirection(relativeDirections(getFacingDirection())[2]).getGameObject() == null){
            updater.addToUpdateList(GameObjectFactory.ShootGunBullet(getCurrentTile().getTileInDirection(relativeDirections(getFacingDirection())[2]), getFacingDirection(), 50, updater, true, 7 ));
        }
    }

    @Override
    public void handle(ComponentEvent event){
        super.handle(event);
        if(event instanceof CollisionEvent){
            collision = ((CollisionEvent)event).collisonDirection();
        }
    }

    /**
     * Takes a direction and gives you a array with the directons relative to the direction you gave.
     * @param direction The direction that you wants relative directions from
     * @return Array of directions relative to the direction that you gave.
     */
    protected Direction[] relativeDirections(Direction direction){
        Direction[] relativeDirections = new Direction[4];
        switch (direction){

            case LEFT:
                relativeDirections[0] = Direction.LEFT;
                relativeDirections[1] = Direction.UP;
                relativeDirections[2] = Direction.DOWN;
                relativeDirections[3] = Direction.RIGHT;
                break;

            case RIGHT:
                relativeDirections[0] = Direction.RIGHT;
                relativeDirections[1] = Direction.DOWN;
                relativeDirections[2] = Direction.UP;
                relativeDirections[3] = Direction.LEFT;
                break;

            case DOWN:
                relativeDirections[0] = Direction.DOWN;
                relativeDirections[1] = Direction.RIGHT;
                relativeDirections[2] = Direction.LEFT;
                relativeDirections[3] = Direction.UP;
                break;

            default:
                relativeDirections[0] = Direction.UP;
                relativeDirections[1] = Direction.LEFT;
                relativeDirections[2] = Direction.RIGHT;
                relativeDirections[3] = Direction.DOWN;
                break;
        }
        return relativeDirections;
    }

}