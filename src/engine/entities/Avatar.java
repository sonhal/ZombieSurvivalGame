package engine.entities;


import engine.controllers.Direction;
import engine.entities.composites.*;
import engine.entities.items.weapons.Weapon;

import java.util.List;

/**
 * Reperesent the Avatar in the game that the player controls.
 */
public class Avatar extends LivingObject {


    private AttackComponent attackComponent;
    private CollisionComponent collisionComponent;
    private double moveTime;
    private double moveDelay;
    private Direction isMoving;
    private double movingTimeCounter;


    public Avatar(GraphicsComponent gc, HealthComponent hc,
                  AttackComponent ac, CollisionComponent cc) {
        super(gc, hc);
        this.attackComponent = ac;
        this.collisionComponent = cc;
    }

    public Avatar(Sprite sprite){
        super(sprite);
        this.attackComponent = new AttackComponent();
        this.collisionComponent = new CollisionComponent();
    }

    public Avatar(){
        this(new Sprite(2));
    }


    /**
     * Tells players Avatar object to shoot in the direction parameter specifies.
     */
    public void attack(Direction attackDirection){
        //currently shoots in facing direction
        attackComponent.attack(getTransformComponent().getFacingDirection(), getTransformComponent());
    }


    /**
     * Handles the pickup of Weapons for the Avatar.
     * @param weapon reference to the weapon to be picked up.
     */
    public void pickupWeapon(Weapon weapon){
        attackComponent.setWeapon(weapon);
    }

    @Override
    public void move(Direction direction){
        if(!this.collisionComponent.collisionDetect(this.getTransformComponent().getCurrentTile(), direction)){
            this.collisionComponent.clearCollided();
            this.getTransformComponent().move(direction);
        }
        this.getTransformComponent().setFacingDirection(direction);
        this.getGraphicsComponent().setActiveSpriteByID(getSpriteIDByDirection(direction));
    }


    public int getSpriteIDByDirection(Direction direction){
        int spriteId = 0;
        switch (direction){
            case DOWN:
                spriteId = 4;
                break;
            case UP:
                spriteId = 3;
                break;
            case LEFT:
                spriteId = 2;
                break;
            case RIGHT:
                spriteId = 1;
                break;
        }
        return spriteId;
    }

    public int getSpriteMovingIDByDirection(Direction direction){
        switch (direction){
            case UP: return  7;
            case DOWN: return 8;
            case LEFT: return 5;
            case RIGHT: return 6;
            default: return 0;
        }
    }

    public void handleMoving(Direction direction){
        if(canMove()){
            this.move(direction);
            this.moveTime = System.currentTimeMillis();
            if(isMoving == null) {
                movingTimeCounter = System.currentTimeMillis();
            }
            this.isMoving = direction;
        }
    }


    private boolean canMove(){
        return System.currentTimeMillis() > this.moveTime + moveDelay;
    }

    public void setMoveDelay(double moveDelay) {
        if (moveDelay > 0){
            this.moveDelay = moveDelay;
        }
        else {System.out.println("Cant set move delay less than 0");}
    }

    public void updateIsMoving(){
        if(this.isMoving != null){
            if(movingTimeCounter + 100 < System.currentTimeMillis()){
                this.getGraphicsComponent().setActiveSpriteByID(getSpriteIDByDirection(isMoving));
                isMoving = null;
            }
            else {
                getGraphicsComponent().setActiveSpriteByID(getSpriteMovingIDByDirection(isMoving));
            }
        }
    }

    public CollisionComponent getCollisionComponent() {
        return collisionComponent;
    }

    public Direction getIsMoving() {
        return isMoving;
    }
}
