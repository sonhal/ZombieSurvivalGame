package engine.entities.items.weapons;

import engine.controllers.Direction;
import engine.entities.components.AttackComponent;
import engine.services.TimeService;
import engine.world.Tile;

/**
 * Weapon subclass
 * Represent a close range weapon
 */
public class MeleeWeapon extends Weapon {

    private double lastActivateTime;
    private double activateDelay;

    /**
     *
     * @param attackComponent
     * @param activateDelay
     */
    public MeleeWeapon(double activateDelay, AttackComponent attackComponent) {
        super(attackComponent);
        this.lastActivateTime = System.currentTimeMillis();
        this.activateDelay = activateDelay;
    }

    @Override
    public boolean activate(Tile fromTile, Direction direction) {
        if(TimeService.canUpdate(activateDelay, lastActivateTime)){
            System.out.println("Weapon activated!");
            attackComponent.tryAttack(fromTile.getTileInDirection(direction));
            lastActivateTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

}
