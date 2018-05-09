package engine.entities.items.weapons;


import engine.entities.components.SingleAttackComponent;
import engine.world.Tile;
import engine.controllers.Direction;

import java.io.Serializable;

/**
* Represents a weapon a player can pickup and use in the game.
 */
public abstract class Weapon implements Serializable{

    protected SingleAttackComponent attackComponent;

    public Weapon(SingleAttackComponent attackComponent){
        this.attackComponent  = attackComponent;
    }


    public abstract boolean activate(Tile fromTile, Direction direction);

    /**
     * Method to get the damage the weapon deals
     * @return the damage the weapon deals
     */
    public SingleAttackComponent getAttackComponent() {
        return attackComponent;
    }



}
