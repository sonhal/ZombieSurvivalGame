package engine;

public class Tile {

    Tile up;
    Tile down;
    Tile left;
    Tile right;

    int cordX;
    int cordY;

    GameObject gameObject;
    Item item;



    public Tile(int x, int y){
        cordX = x;
        cordY = y;
    }

    public Tile getUp() {
        return up;
    }

    public void setUp(Tile up) {
        this.up = up;
    }

    public Tile getDown() {
        return down;
    }

    public void setDown(Tile down) {
        this.down = down;
    }

    public Tile getLeft() {
        return left;
    }

    public void setLeft(Tile left) {
        this.left = left;
    }

    public Tile getRight() {
        return right;
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
        this.gameObject = gameObject;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}