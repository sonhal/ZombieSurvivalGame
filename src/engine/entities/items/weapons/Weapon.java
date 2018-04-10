package engine.entities.items.weapons;


import engine.entities.world.Tile;
import engine.controllers.Direction;

import java.io.Serializable;
import java.security.InvalidParameterException;

/**
* Represents a weapon a player can pickup and use in the game.
 */
public abstract class Weapon implements Serializable{

    private int damage;

    /**
     * Sets the damage for the weapon
     * @param damage the damage the weapon deals
     */
    public Weapon(int damage){
        if(damage < 1){
            throw new InvalidParameterException("A Weapons damage cannot be lower than 1");
        }
        this.damage = damage;
    }


    public abstract void activate(Tile fromTile, Direction direction);

    /**
     * Method to get the damage the weapon deals
     * @return the damage the weapon deals
     */
    public int getDamage() {
        return damage;
    }



}
