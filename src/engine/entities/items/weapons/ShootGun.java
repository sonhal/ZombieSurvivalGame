package engine.entities.items.weapons;

import engine.controllers.Direction;
import engine.controllers.Updater;
import engine.entities.components.SingleAttackComponent;
import engine.entities.components.SoundEffectComponent;
import engine.entities.gameobjects.GameObjectFactory;
import engine.entities.gameobjects.Sprite;
import engine.services.TimeService;
import engine.world.Tile;

/**
 * Weapon subclass that fires Bullets.
 * Is meant to be held and activated by a WeaponComponent
 */
public class ShootGun extends Gun {

    private static final WeaponType type = WeaponType.SHOT_GUN;

    public ShootGun(WeaponType weaponType, SoundEffectComponent soundEffectComponent, SingleAttackComponent attackComponent, Updater updater, double activateDelay, int range, int ammo) {
        super(type, soundEffectComponent, attackComponent, updater, activateDelay, range, ammo);
    }

    @Override
    protected void tryAttack(Tile startTile, Direction direction, Updater updater){
        if (startTile.getGameObject() == null){
            System.out.println("Amunition left on activeWeapon: " + ammo);
            updater.addToUpdateList(GameObjectFactory.ShootGunBullet(startTile, direction, attackComponent.getDamage(), updater, false, 9));
            ammo--;
        }
        else {
            meleeAttack(attackComponent, startTile);
        }

    }

}
