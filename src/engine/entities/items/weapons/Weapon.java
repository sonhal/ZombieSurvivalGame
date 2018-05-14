package engine.entities.items.weapons;


import engine.controllers.Updater;
import engine.entities.components.SingleAttackComponent;
import engine.entities.components.interfaces.AttackComponent;
import engine.entities.gameobjects.Sprite;
import engine.entities.items.Item;
import engine.services.TimeService;
import engine.world.Tile;
import engine.controllers.Direction;

import java.io.Serializable;

/**
* Represents a weapon a player can pickup and use in the game.
 */
public abstract class Weapon implements Serializable{

    protected WeaponType weaponType;
    protected SingleAttackComponent attackComponent;
    protected Updater updater;
    protected double lastActivateTime;
    protected double activateDelay;
    protected int range;
    protected int ammo;
    protected Sprite sprite;

    public Weapon(WeaponType weaponType, SingleAttackComponent attackComponent, Updater updater, double activateDelay, int range, int ammo){
        this.weaponType = weaponType;
        this.attackComponent  = attackComponent;
        this.updater = updater;
        this.lastActivateTime = System.currentTimeMillis();
        this.activateDelay = activateDelay;
        this.range = range;
        this.ammo = ammo;

    }

    protected Weapon(SingleAttackComponent attackComponent, double activateDelay) {
            this.attackComponent  = attackComponent;
            this.lastActivateTime = System.currentTimeMillis();
            this.activateDelay = activateDelay;
    }

    protected Weapon() {
    }

    public boolean activate(Tile fromTile, Direction direction) {
            System.out.println("Amunition left on weapon: " + ammo);
        if(TimeService.canUpdate(activateDelay, lastActivateTime) && (ammo > 0 || ammo < -40)){
            System.out.println("Weapon activated!");
            Tile startTile = fromTile.getTileInDirection(direction);
            //if adjacent Tile is occupied, hit StaticGameObject on Tile
            if (startTile.getGameObject() != null){
                //tryAttack(attackComponent, startTile);
                addAttackToUpdateList(startTile, direction, attackComponent.getDamage(), updater);
                ammo--;
            }
            //else instantiate Bullet and add it to the controllers update list
            else {
                addAttackToUpdateList(startTile, direction, attackComponent.getDamage(), updater);
                ammo--;
            }
            System.out.println("Gun fired!");
            lastActivateTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    protected abstract void addAttackToUpdateList(Tile startTile, Direction direction, int damage, Updater updater);
    protected abstract void tryAttack(AttackComponent attackComponent, Tile startTile);

    /**
     * Method to get the damage the weapon deals
     * @return the damage the weapon deals
     */
    public SingleAttackComponent getAttackComponent() {
        return attackComponent;
    }

    public void setController(Updater updater) {
        this.updater = updater;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) { this.ammo = ammo; }

    public Sprite getSprite() {
        return sprite;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }
}
