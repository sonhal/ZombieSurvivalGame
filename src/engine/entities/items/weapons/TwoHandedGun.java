package engine.entities.items.weapons;

import engine.controllers.Direction;
import engine.controllers.Updater;
import engine.entities.components.SingleAttackComponent;
import engine.entities.components.SoundEffectComponent;
import engine.entities.gameobjects.GameObjectFactory;
import engine.entities.gameobjects.Sprite;
import engine.services.TimeService;
import engine.world.Tile;

import java.nio.channels.spi.SelectorProvider;

/**
 * Weapon subclass which fires two bullets, each which travels to the left and the right of the player.
 * Fireing this bullet is consuming two ammo. If only one bullet in the magazine, only the bullet to right will fire.
 * Is meant to be held and activated by a WeaponComponent
 */
public class TwoHandedGun extends Weapon {

    private static final WeaponType type = WeaponType.TWO_HANDED_GUN;

    protected int range;
    protected int ammo;

    public TwoHandedGun(WeaponType weaponType, SoundEffectComponent soundEffectComponent, SingleAttackComponent attackComponent, Updater updater, double activateDelay, int range, int ammo) {
        super(type, soundEffectComponent, attackComponent, updater, activateDelay);
        this.range = range;
        this.ammo = ammo;
    }

    @Override
    protected void tryAttack(Tile startTile, Direction direction, Updater updater){
        if (startTile.getGameObject() == null){
            Tile fromTile = startTile.getTileInDirection(relativeDirections(direction)[3]);

            if (fromTile.getTileInDirection(relativeDirections(direction)[1]).getGameObject() == null) {
                updater.addToUpdateList(GameObjectFactory.explodingBullet(fromTile.getTileInDirection(relativeDirections(direction)[1]), relativeDirections(direction)[1], attackComponent.getDamage(), updater));
                ammo--;
            }else{
                meleeAttack(attackComponent, fromTile.getTileInDirection(relativeDirections(direction)[1]));
            }

            if (fromTile.getTileInDirection(relativeDirections(direction)[2]).getGameObject() == null) {
                if (getAmmo() > 0) {
                    updater.addToUpdateList(GameObjectFactory.explodingBullet(fromTile.getTileInDirection(relativeDirections(direction)[2]), relativeDirections(direction)[2], attackComponent.getDamage(), updater));
                    ammo--;
                }
            }else{
                meleeAttack(attackComponent, fromTile.getTileInDirection(relativeDirections(direction)[2]));
            }
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
