package engine;


import engine.composites.Sprite;

/**
* Represents a weapon a player can pickup and use in the game.
 */
public class Weapon extends Item{

    private int damage;
    private int range;

    /**
     * Sets the damage for the weapon
     * @param damage the damage the weapon deals
     */
    public Weapon(Sprite sprite, int range, int damage){
        super(sprite);
        this.damage = damage;
        this.range = range;

    }

    /**
     * Activate the weapon making it try to apply damage to every GameObject on every Tile its range
     * @param fromTile
     * @param direction
     */
    public void activate(Tile fromTile, Direction direction){
        Tile tileToBeAttacked = null;
        for (int i = range; range > 0; i--){
            switch (direction){
                case UP: tileToBeAttacked = fromTile.getUp();
                    break;
                case DOWN: tileToBeAttacked = fromTile.getDown();
                    break;
                case LEFT: tileToBeAttacked = fromTile.getLeft();
                    break;
                case RIGHT: tileToBeAttacked = fromTile.getRight();
                    break;
            }
            attackTile(tileToBeAttacked);


        }
    }



    /**
     * Method to get the damage the weapon deals
     * @return the damage the weapon deals
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Try to damage GameObject on the parameter tile.
     * @param tile
     */
    private void attackTile(Tile tile){
        if ( tile != null && tile.getGameObject() != null){
            if(tile.getGameObject() instanceof LivingObject){
               LivingObject living = (LivingObject)tile.getGameObject();
               living.damage(damage);
            }
        }
    }

}
