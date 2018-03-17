package engine;


import engine.composites.*;
import javafx.scene.canvas.GraphicsContext;

/**
 * Reperesent the Avatar in the game that the player controls.
 */
public class Avatar extends LivingObject{


    private AttackComponent attackComponent;
    private CollisionComponent collisionComponent;

    public Avatar(TransformComponent tc, GraphicsComponent gc, HealthComponent hc,
                  AttackComponent ac, CollisionComponent cc) {
        super(tc, gc, hc);
        this.attackComponent = ac;
        this.collisionComponent = cc;
    }


    /**
     * Tells players Avatar object to shoot in the direction parameter specifies.
     */
    public void attack(Direction attackDirection){
        attackComponent.attack(attackDirection);
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
            this.getTransformComponent().move(direction);
        }
        this.getGraphicsComponent().setActiveSpriteByID(getSpriteIDByDirection(direction));
    }


    private int getSpriteIDByDirection(Direction direction){
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


}
