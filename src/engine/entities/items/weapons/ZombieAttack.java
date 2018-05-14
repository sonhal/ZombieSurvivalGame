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
public class ZombieAttack extends Weapon {
    SingleAttackComponent attackComponent = new SingleAttackComponent(4);
        double lastActivateTime = System.currentTimeMillis();
        double activateDelay = 4;

    public ZombieAttack() {


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

    @Override
    protected void addAttackToUpdateList(Tile startTile, Direction direction, int damage, Updater updater){

    }

    protected void tryAttack(AttackComponent attackComponent, Tile startTile){
        attackComponent.tryAttack(startTile);
    }

    @Override
    protected boolean canActivate() {
        return false;
    }
}
