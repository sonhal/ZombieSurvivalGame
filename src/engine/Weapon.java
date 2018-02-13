package engine;



/**
* Represents a weapon a player can pickup and use in the game.
 */
public class Weapon extends Item{

    private int damage;

    /**
     * Sets the damage for the weapon
     * @param damage the damage the weapon deals
     */
    public Weapon(int damage){
        this.damage = damage;
    }



    /**
     * Method to get the damage the weapon deals
     * @return the damage the weapon deals
     */
    public int getDamage() {
        return damage;
    }
}
