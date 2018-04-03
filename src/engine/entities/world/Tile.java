package engine.entities.world;

import engine.controllers.Direction;
import engine.entities.GameObject;
import engine.entities.composites.Sprite;
import engine.entities.items.Item;

public class Tile {

    Tile up;
    Tile down;
    Tile left;
    Tile right;

    int cordX;
    int cordY;

    GameObject gameObject;
    Item item;
    Sprite sprite;



    public Tile(int x, int y, Sprite sprite){
        cordX = x;
        cordY = y;
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Tile getUp() {
        if (up != null){
            return up;
        }
        else return this;
    }

    public void setUp(Tile up) {
        this.up = up;
    }

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
            System.out.println("ERROR: You tried to pass null object to a Tile");
        }
    }

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

    @Override
    public String toString() {
        return super.toString() +": location x: " + getCordX() +", y: " + getCordY();
    }
}
