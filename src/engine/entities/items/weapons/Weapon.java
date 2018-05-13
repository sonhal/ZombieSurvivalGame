package engine.entities.items.weapons;


import engine.controllers.Updater;
import engine.entities.components.SingleAttackComponent;
import engine.entities.components.interfaces.AttackComponent;
import engine.entities.gameobjects.Sprite;
import engine.entities.items.Item;
import engine.services.TimeService;
import engine.world.Tile;
import engine.controllers.Direction;

import java.io.Serializable;

/**
* Represents a weapon a player can pickup and use in the game.
 */
public abstract class Weapon implements Serializable{

    protected SingleAttackComponent attackComponent;
    protected Updater updater;
    protected double lastActivateTime;
    protected double activateDelay;
    protected int range;

    public Weapon(SingleAttackComponent attackComponent, Updater updater, double activateDelay, int range){
        this.attackComponent  = attackComponent;
        this.updater = updater;
        this.lastActivateTime = System.currentTimeMillis();
        this.activateDelay = activateDelay;
        this.range = range;

    }

    protected Weapon(SingleAttackComponent attackComponent, double activateDelay) {
            this.attackComponent  = attackComponent;
            this.lastActivateTime = System.currentTimeMillis();
            this.activateDelay = activateDelay;
    }

    protected Weapon() {
    }

    public boolean activate(Tile fromTile, Direction direction) {
        if(TimeService.canUpdate(activateDelay, lastActivateTime)){
            System.out.println("Weapon activated!");
            Tile startTile = fromTile.getTileInDirection(direction);
            //if adjacent Tile is occupied, hit StaticGameObject on Tile
            if (startTile.getGameObject() != null){
                tryAttack(attackComponent, startTile);
            }
            //else instantiate Bullet and add it to the controllers update list
            else {
                addAttackToUpdateList(startTile, direction, attackComponent.getDamage(), updater);
            }
            System.out.println("Gun fired!");
            lastActivateTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    protected abstract void addAttackToUpdateList(Tile startTile, Direction direction, int damage, Updater updater);
    protected abstract void tryAttack(AttackComponent attackComponent, Tile startTile);

    /**
     * Method to get the damage the weapon deals
     * @return the damage the weapon deals
     */
    public SingleAttackComponent getAttackComponent() {
        return attackComponent;
    }

    public void setController(Updater updater) {
        this.updater = updater;
    }
}
