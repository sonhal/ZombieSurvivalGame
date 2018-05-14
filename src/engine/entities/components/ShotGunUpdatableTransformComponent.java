package engine.entities.components;

import engine.controllers.Direction;
import engine.controllers.Updater;
import engine.entities.components.ComponentEvent.CollisionEvent;
import engine.entities.components.ComponentEvent.ComponentEvent;
import engine.entities.gameobjects.GameObjectFactory;
import engine.entities.gameobjects.interfaces.GameObject;
import engine.world.Tile;


public class ShotGunUpdatableTransformComponent extends UpdatableTransformComponent {

int spreadAfter;
int traveledDistanse;
boolean haveSpread;
Updater updater;


    public ShotGunUpdatableTransformComponent(Tile connectedTile, int spreadAfter, boolean haveSpread, Updater updater) {
        super(connectedTile);
        this.spreadAfter = spreadAfter;
        this.updater = updater;
        this.haveSpread = haveSpread;
    }

    public void move(Direction direction, GameObject gameObject){
            super.move(direction, gameObject);
            setFacingDirection(direction);
    }

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