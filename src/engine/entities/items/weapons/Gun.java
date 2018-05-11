package engine.entities.items.weapons;

import engine.controllers.Updater;
import engine.entities.gameobjects.GameObjectFactory;
import engine.entities.components.*;
import engine.services.TimeService;
import engine.world.Tile;
import engine.controllers.Direction;

/**
 * Weapon subclass that fires Bullets.
 * Is meant to be held and activated by a SingleWeaponComponent
 */
public class Gun extends Weapon {

    private Updater controller;
    private double lastActivateTime;
    private double activateDelay;
    private int range;

    public Gun(Updater controller, int range, double activateDelay, SingleAttackComponent attackComponent) {
        super(attackComponent);
        this.controller = controller;
        this.lastActivateTime = System.currentTimeMillis();
        this.activateDelay = activateDelay;
        this.range = range;
    }

    public void setController(Updater controller) {
        this.controller = controller;
    }

    @Override
    public boolean activate(Tile fromTile, Direction direction) {
        if(TimeService.canUpdate(activateDelay, lastActivateTime)){

            System.out.println("Weapon activated!");
            Tile startTile = fromTile.getTileInDirection(direction);

            //if adjacent Tile is occupied, hit StaticGameObject on Tile
            if (startTile.getGameObject() != null){
                attackComponent.tryAttack(startTile);
            }
            //else instantiate Bullet and add it to the controllers update list
            else {
                controller.addToUpdateList(GameObjectFactory.createBullet(startTile, direction, attackComponent.getDamage()));
            }
            lastActivateTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }


}
