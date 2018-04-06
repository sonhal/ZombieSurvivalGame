package engine.entities.items.weapons;

import engine.controllers.Direction;
import engine.entities.composites.AttackComponent;
import engine.entities.composites.TimeComponent;
import engine.entities.world.Tile;

/**
 * Weapon subclass
 * Represent a close range weapon
 */
public class MeleeWeapon extends Weapon {

    private double lastActivateTime;
    private double activateDelay;

    /**
     * Sets the damage for the weapon
     *
     * @param range
     * @param damage the damage the weapon deals
     */
    public MeleeWeapon(int range, int damage, double activateDelay) {
        super(damage);
        this.lastActivateTime = System.currentTimeMillis();
        this.activateDelay = activateDelay;
    }

    @Override
    public void activate(Tile fromTile, Direction direction) {
        if(TimeComponent.canUpdate(activateDelay, lastActivateTime)){
            System.out.println("Weapon activated!");
            AttackComponent.tryAttack(getDamage(), fromTile.getTileInDirection(direction));
            lastActivateTime = System.currentTimeMillis();
        }
    }

}
