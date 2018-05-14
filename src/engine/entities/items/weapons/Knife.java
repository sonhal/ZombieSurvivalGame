package engine.entities.items.weapons;

import engine.controllers.Updater;
import engine.entities.components.interfaces.AttackComponent;
import engine.entities.gameobjects.GameObjectFactory;
import engine.entities.components.*;
import engine.services.TimeService;
import engine.world.Tile;
import engine.controllers.Direction;

/**
 * Weapon subclass that fires Bullets.
 * Is meant to be held and activated by a SingleWeaponComponent
 */
public class Knife extends Weapon {

    public Knife(WeaponType weaponType, SingleAttackComponent attackComponent, Updater updater, double activateDelay, int range, int ammo) {
        super(weaponType, attackComponent, updater, activateDelay, range, ammo);

    }

    @Override
    protected void addAttackToUpdateList(Tile startTile, Direction direction, int damage, Updater updater){
        tryAttack(attackComponent, startTile);
        updater.addToUpdateList(GameObjectFactory.knifeAttackParticleEffect(startTile, direction, 1));
    }

    protected void tryAttack(AttackComponent attackComponent, Tile startTile){

        attackComponent.tryAttack(startTile);
    }
}
