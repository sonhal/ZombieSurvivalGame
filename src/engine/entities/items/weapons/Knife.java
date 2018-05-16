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

    private static final WeaponType type = WeaponType.BASIC_KNIFE;

    public Knife(AttackSoundEffectComponent soundEffectComponent, SingleAttackComponent attackComponent, Updater updater, double activateDelay) {
        super(type, soundEffectComponent, attackComponent, updater, activateDelay);
    }

    @Override
    protected void tryAttack(Tile startTile, Direction direction, Updater updater){
        meleeAttack(attackComponent, startTile);
        updater.addToUpdateList(GameObjectFactory.knifeAttackParticleEffect(startTile, direction, 1));
    }

    protected void meleeAttack(AttackComponent attackComponent, Tile startTile){
        attackComponent.tryAttack(startTile);
    }

    @Override
    protected boolean canActivate() {
        return TimeService.canUpdate(activateDelay, lastActivateTime);
    }

}
