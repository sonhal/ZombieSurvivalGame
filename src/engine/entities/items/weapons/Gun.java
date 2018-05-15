package engine.entities.items.weapons;

import engine.controllers.Updater;
import engine.entities.components.interfaces.AttackComponent;
import engine.entities.gameobjects.GameObjectFactory;
import engine.entities.components.*;
import engine.entities.gameobjects.Sprite;
import engine.services.TimeService;
import engine.world.Tile;
import engine.controllers.Direction;

/**
 * Weapon subclass that fires Bullets.
 * Is meant to be held and activated by a WeaponComponent
 */
public class Gun extends Weapon {

    private static final WeaponType type = WeaponType.BASIC_GUN;

    protected int range;
    protected int ammo;

    public Gun(SoundEffectComponent soundEffectComponent, SingleAttackComponent attackComponent, Updater updater, double activateDelay, int range, int ammo) {
        super(type, soundEffectComponent, attackComponent, updater, activateDelay);
        this.range = range;
        this.ammo = ammo;
    }

    public Gun(WeaponType weaponType, SoundEffectComponent soundEffectComponent, SingleAttackComponent attackComponent, Updater updater, double activateDelay, int range, int ammo) {
        super(weaponType, soundEffectComponent, attackComponent, updater, activateDelay);
        this.range = range;
        this.ammo = ammo;
    }

    @Override
    protected void tryAttack(Tile startTile, Direction direction, Updater updater){
        if (startTile.getGameObject() == null){
            System.out.println("Amunition left on activeWeapon: " + ammo);
            updater.addToUpdateList(GameObjectFactory.explodingBullet(startTile, direction, attackComponent.getDamage(), updater));
            ammo--;
        }
        else {
            meleeAttack(attackComponent, startTile);
        }

    }


    @Override
    protected boolean canActivate() {
        return TimeService.canUpdate(activateDelay, lastActivateTime) && (ammo > 0 || ammo < -40);
    }

    @Override
    public int getAmmo() {
        return ammo;
    }

    @Override
    public void setAmmo(int ammo) {
        if(ammo > 0){ this.ammo = ammo;}
    }

}
