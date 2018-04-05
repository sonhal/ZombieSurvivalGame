package engine.entities.items.weapons;


import engine.entities.LivingObject;
import engine.entities.world.Tile;
import engine.controllers.Direction;

import java.security.InvalidParameterException;

/**
* Represents a weapon a player can pickup and use in the game.
 */
public abstract class Weapon{

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

    /**
     * Try to damage GameObject on the parameter tile.
     * @param tile
     */
    protected void attackTile(Tile tile){
        if ( tile != null && tile.getGameObject() != null){
            tile.getGameObject().hit(damage);
        }
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
