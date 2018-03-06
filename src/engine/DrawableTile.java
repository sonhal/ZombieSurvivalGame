package engine;

public class DrawableTile {
    private GameObject gameObject;
    private Item item;
    private Sprite sprite;

    DrawableTile(GameObject gameObject, Item item){
        this.gameObject = gameObject;
        this.item = item;
    }
    DrawableTile(){

    }

    public void setGameAndItem(GameObject gameObject, Item item){
        this.gameObject = gameObject;
        this.item = item;
    }

    public Sprite getSprite() {
        return sprite;
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
