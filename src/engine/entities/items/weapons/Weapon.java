package engine.entities.items.weapons;


import engine.controllers.Updater;
import engine.entities.components.ComponentEvent.AttackCompletedEvent;
import engine.entities.components.ComponentEvent.AttackEvent;
import engine.entities.components.SingleAttackComponent;
import engine.entities.components.SoundEffectComponent;
import engine.entities.components.interfaces.AttackComponent;
import engine.entities.gameobjects.Sprite;
import engine.entities.items.Item;
import engine.services.TimeService;
import engine.services.audio.Sound;
import engine.world.Tile;
import engine.controllers.Direction;

import java.io.Serializable;

/**
* Represents a activeWeapon a player can pickup and use in the game.
 */
public abstract class Weapon implements Serializable{

    protected WeaponType weaponType;
    protected SingleAttackComponent attackComponent;
    protected Updater updater;
    protected double lastActivateTime;
    protected double activateDelay;
    protected Sprite sprite;
    protected SoundEffectComponent soundEffect;

    public Weapon(WeaponType weaponType, SoundEffectComponent soundEffectComponent, SingleAttackComponent attackComponent, Updater updater, double activateDelay){
        this.weaponType = weaponType;
        this.attackComponent  = attackComponent;
        this.updater = updater;
        this.lastActivateTime = System.currentTimeMillis();
        this.activateDelay = activateDelay;
        this.soundEffect = soundEffectComponent;

    }

    protected Weapon(SingleAttackComponent attackComponent, double activateDelay) {
            this.attackComponent  = attackComponent;
            this.lastActivateTime = System.currentTimeMillis();
            this.activateDelay = activateDelay;
    }

    protected Weapon() {
    }

    public boolean activate(Tile fromTile, Direction direction) {

        if(canActivate()){
            System.out.println("Weapon activated!");
            soundEffect.handle(new AttackCompletedEvent());
            soundEffect.update(null);

            Tile startTile = fromTile.getTileInDirection(direction);
            addAttackToUpdateList(startTile, direction, attackComponent.getDamage(), updater);
            lastActivateTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    protected abstract void addAttackToUpdateList(Tile startTile, Direction direction, int damage, Updater updater);
    protected abstract void tryAttack(AttackComponent attackComponent, Tile startTile);
    protected abstract boolean canActivate();

    /**
     * Method to get the damage the activeWeapon deals
     * @return the damage the activeWeapon deals
     */
    public SingleAttackComponent getAttackComponent() {
        return attackComponent;
    }

    public void setController(Updater updater) {
        this.updater = updater;
    }

    public int getAmmo() {
        return 0;
    }

    public void setAmmo(int ammo) { };

    public Sprite getSprite() {
        return sprite;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }
}
