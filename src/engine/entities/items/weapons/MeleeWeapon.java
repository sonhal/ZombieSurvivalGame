package engine.entities.items.weapons;

import engine.controllers.Direction;
import engine.entities.world.Tile;

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
        super(range, damage);
        this.lastActivateTime = System.currentTimeMillis();
        this.activateDelay = activateDelay;
    }

    @Override
    public void activate(Tile fromTile, Direction direction) {
        if(canActivate()){
            System.out.println("Weapon activated!");
            attackTile(fromTile.getTileInDirection(direction));
            lastActivateTime = System.currentTimeMillis();
        }
    }

    private boolean canActivate(){
        return System.currentTimeMillis() > this.lastActivateTime + activateDelay;
    }
}
