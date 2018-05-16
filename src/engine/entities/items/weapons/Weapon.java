package engine.entities.items.weapons;


import engine.controllers.Updater;
import engine.entities.components.ComponentEvent.AttackCompletedEvent;
import engine.entities.components.SingleAttackComponent;
import engine.entities.components.AttackSoundEffectComponent;
import engine.entities.components.interfaces.AttackComponent;
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
    protected AttackSoundEffectComponent soundEffect;

    public Weapon(WeaponType weaponType, AttackSoundEffectComponent soundEffectComponent, SingleAttackComponent attackComponent, Updater updater, double activateDelay){
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

    protected abstract void tryAttack(Tile startTile, Direction direction, Updater updater);

    protected abstract boolean canActivate();

    public boolean activate(Tile fromTile, Direction direction) {

        if(canActivate()){
            System.out.println("Weapon activated!");
            soundEffect.handle(new AttackCompletedEvent());
            soundEffect.update(null);

            Tile startTile = fromTile.getTileInDirection(direction);
            tryAttack(startTile, direction, updater);
            lastActivateTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    protected void meleeAttack(AttackComponent attackComponent, Tile startTile){
        attackComponent.tryAttack(startTile);
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


    /**
     * Method to get the damage the activeWeapon deals
     * @return the damage the activeWeapon deals
     */
    public AttackComponent getAttackComponent() {
        return attackComponent;
    }

    public void setController(Updater updater) {
        this.updater = updater;
    }

    public int getAmmo() {
        return 0;
    }

    public void setAmmo(int ammo) { };


    public WeaponType getWeaponType() {
        return weaponType;
    }


}
