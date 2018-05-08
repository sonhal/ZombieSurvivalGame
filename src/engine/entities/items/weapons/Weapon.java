package engine.entities.items.weapons;


import engine.entities.components.AttackComponent;
import engine.world.Tile;
import engine.controllers.Direction;

import java.io.Serializable;
import java.security.InvalidParameterException;

/**
* Represents a weapon a player can pickup and use in the game.
 */
public abstract class Weapon implements Serializable{

    protected AttackComponent attackComponent;

    public Weapon(AttackComponent attackComponent){
        this.attackComponent  = attackComponent;
    }


    public abstract boolean activate(Tile fromTile, Direction direction);

    /**
     * Method to get the damage the weapon deals
     * @return the damage the weapon deals
     */
    public AttackComponent getAttackComponent() {
        return attackComponent;
    }



}
