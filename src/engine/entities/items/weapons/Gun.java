package engine.entities.items.weapons;

import engine.entities.composites.AttackComponent;
import engine.entities.composites.TimeComponent;
import engine.entities.world.Tile;
import engine.controllers.Direction;
import engine.controllers.ScriptableObjectUpdater;

/**
 * Weapon subclass that fires Bullets.
 * Is meant to be held and activated by a WeaponComponent
 */
public class Gun extends Weapon {

    private ScriptableObjectUpdater controller;
    private double lastActivateTime;
    private double activateDelay;
    private int range;

    public Gun(ScriptableObjectUpdater controller, int range, int damage, double activateDelay) {
        super(damage);
        this.controller = controller;
        this.lastActivateTime = System.currentTimeMillis();
        this.activateDelay = activateDelay;
        this.range = range;
    }

    @Override
    public void activate(Tile fromTile, Direction direction) {
        if(TimeComponent.canUpdate(activateDelay, lastActivateTime)){

            System.out.println("Weapon activated!");
            Tile startTile = fromTile.getTileInDirection(direction);

            //if adjacent Tile is occupied, hit GameObject on Tile
            if (startTile.getGameObject() != null){
                AttackComponent.tryAttack(getDamage(), startTile);
            }
            //else instantiate Bullet and add it to the controllers update list
            else {
                controller.addToUpdateList(new Bullet(range, this.getDamage(), 50, startTile, direction));
            }

        }
    }


}
