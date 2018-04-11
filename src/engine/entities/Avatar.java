package engine.entities;


import engine.entities.composites.*;
import engine.entities.interfaces.Collidable;
import engine.entities.interfaces.Hittable;
import engine.entities.items.weapons.Weapon;


/**
 * A GameObject in the world that can move, attack and take damage.
 */
public class Avatar extends UpdatableGameObject implements Hittable, Collidable {


    private WeaponComponent weaponComponent;
    private CollisionComponent collisionComponent;
    private HealthComponent healthComponent;
    private InputComponent inputComponent;
    private boolean isPlayer;

    public Avatar(AvatarGraphicsComponent gc, HealthComponent hc,
                  WeaponComponent ac, CollisionComponent cc, InputComponent ic, AvatarTransformComponent tc) {
        super(gc);
        this.healthComponent = hc;
        this.weaponComponent = ac;
        this.collisionComponent = cc;
        this.inputComponent = ic;
        setTransformComponent(tc);
        tc.setGameObject(this);
    }

    /**
     * Handles the pickup of Weapons for the Avatar.
     * @param weapon reference to the weapon to be picked up.
     */
    public void pickupWeapon(Weapon weapon){
        weaponComponent.setWeapon(weapon);
    }

    /**
     * Hit the Object
     * @param hitDamage damage to apply to the object
     */
    @Override
    public void hit(int hitDamage) {
        healthComponent.damage(hitDamage);
    }

    /**
     * @return boolean: Is the Object hit
     */
    @Override
    public boolean isHit() {
        healthComponent.updateDamagedStatus();
        return healthComponent.isDamaged();
    }

    @Override
    public void update() {
        if (checkIfAlive()){
            inputComponent.update(this);
            collisionComponent.update(this);
            getTransformComponent().update(this);
            weaponComponent.update(this);
            getGraphicsComponent().update(this);
            healthComponent.updateDamagedStatus();
        }
    }

    private boolean checkIfAlive(){
        if(!healthComponent.isAlive()){
            getTransformComponent().getCurrentTile().clearGameObject();
            die();
            return false;
        }
        return true;
    }

    public InputComponent getInputComponent() {
        return inputComponent;
    }

    public WeaponComponent getWeaponComponent() {
        return weaponComponent;
    }

    public CollisionComponent getCollisionComponent() {
        return collisionComponent;
    }

    public void setAsPlayer(boolean bool){
        isPlayer = bool;
    }

    public boolean isPlayer(){
        return isPlayer;
    }
}
