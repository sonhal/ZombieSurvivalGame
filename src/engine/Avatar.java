package engine;


import engine.composites.*;

/**
 * Reperesent the Avatar in the game that the player controls.
 */
public class Avatar extends LivingObject{

    private InputComponent inputComponent;
    private AttackComponent attackComponent;

    public Avatar(TransformComponent tc, GraphicsComponent gc, HealthComponent hc, InputComponent ic, AttackComponent ac) {
        super(tc, gc, hc);
        this.inputComponent = ic;
        this.attackComponent = ac;
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


}


