package engine.entities.items.weapons;

import engine.controllers.Updater;
import engine.entities.components.ComponentEvent.AttackCompletedEvent;
import engine.entities.components.interfaces.AttackComponent;
import engine.entities.gameobjects.GameObjectFactory;
import engine.entities.components.*;
import engine.entities.gameobjects.Sprite;
import engine.services.TimeService;
import engine.world.Tile;
import engine.controllers.Direction;

/**
 * Weapon subclass that fires Bullets.
 * Is meant to be held and activated by a SingleWeaponComponent
 */
public class ZombieAttack extends Weapon {

    public ZombieAttack(SoundEffectComponent soundEffectComponent, SingleAttackComponent attackComponent,double activateDelay) {
        super(WeaponType.ZOMBIE_ATTACK, soundEffectComponent, attackComponent, null, activateDelay);
    }


    @Override
    public boolean activate(Tile fromTile, Direction direction) {
        if(TimeService.canUpdate(activateDelay, lastActivateTime)){
            tryAttack(fromTile, direction, updater);
            return true;
        }
        return false;
    }


    @Override
    protected void tryAttack(Tile startTile, Direction direction, Updater updater) {
        System.out.println("Weapon activated!");
        soundEffect.handle(new AttackCompletedEvent());
        soundEffect.update(null);
        attackComponent.tryAttack(startTile.getTileInDirection(direction));
        lastActivateTime = System.currentTimeMillis();
    }

    @Override
    protected boolean canActivate() {
        return false;
    }

}
