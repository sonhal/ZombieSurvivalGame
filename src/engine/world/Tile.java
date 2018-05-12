package engine.world;

import engine.controllers.Direction;
import engine.entities.gameobjects.interfaces.GameObject;
import engine.entities.gameobjects.Sprite;
import engine.entities.items.Item;

import java.io.Serializable;

/** Tile Call containing the state for each tile on the board.
 * Implements the serializable interface in order to make it posible to extract the data to a binary file.
 */

public class Tile implements Serializable{

    private Tile up;
    private Tile down;
    private Tile left;
    private Tile right;

    private int cordX;
    private int cordY;

    private GameObject particleEffect;
    private GameObject gameObject;
    private Item item;
    private Sprite sprite;


    /**
     * Constructor class. Setts the cordinates, and tile sprite on creation.
     * @param x X cordinate.
     * @param y Y cordinate.
     * @param sprite Sprite which gets assigned to the tile.
     */
    public Tile(int x, int y, Sprite sprite){
        cordX = x;
        cordY = y;
        this.sprite = sprite;
    }

    /**
     * Gets the sprite from the tile. Typically used if you are gonna render the gameboard into a image
     * @return
     */
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * Get the tile located in front of the current tile.
     * @return top tile.
     */
    public Tile getUp() {
        if (up != null){
            return up;
        }
        else return this;
    }

    /**
     * Sets refference to the tile which is in front of the current tile
     * @param up Tile where the x cordinate is the same, but y cordinate is +1 of this one
     */
    public void setUp(Tile up) {
        this.up = up;
    }

    /**
     * Retturns the refference to the tile mapped beneath this tile.
     * Can also creativily be used as a portal, or looping the world
     * @return tile object mapped beneath this.
     */
    public Tile getDown() {
        if (down != null){
            return down;
        }
        else return this;
    }

    public void setDown(Tile down) {
        this.down = down;
    }

    public Tile getLeft() {
        if (left != null){
            return left;
        }
        else return this;
    }

    public void setLeft(Tile left) {
        this.left = left;
    }

    public Tile getRight() {
        if (right != null){
            return right;
        }
        else return this;
    }

    public void setRight(Tile right) {
        this.right = right;
    }

    public String getPos(){
        return "x: " + cordX + " y: " + cordY;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        if (gameObject != null){
            this.gameObject = gameObject;
        }
        else{
            System.out.println("WARNING: You tried to pass null object to a Tile");
        }
    }

    public void setParticleEffect(GameObject particleEffect) {
        if(particleEffect != null){
            this.particleEffect = particleEffect;
        }
    }

    public GameObject getParticleEffect() {
        return particleEffect;
    }
    public void clearParticleEffect(){
        particleEffect = null;
    }

    /**
     * Sets the gameobject refference on this tile to null.
     */
    public void clearTile(){
        gameObject = null;
    }


    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void clearGameObject(){
        this.gameObject = null;
    }

    public int getCordX() {
        return cordX;
    }

    public int getCordY() {
        return cordY;
    }

    /**
     * Gets tile in direction of ENUM direction
     * @param direction Enum containing a direction
     * @return Tile in specified direction of this.
     */
    public Tile getTileInDirection(Direction direction){
        switch (direction){
            case UP:
                return getUp();
            case DOWN:
                return getDown();
            case LEFT:
                return getLeft();
            case RIGHT:
                return getRight();
            default:
                return this;
        }
    }

    /**
     * To string method used when testing.
     * @return String describing X and y cordinate of this tile.
     */
    @Override
    public String toString() {
        return super.toString() +": location x: " + getCordX() +", y: " + getCordY();
    }
}
