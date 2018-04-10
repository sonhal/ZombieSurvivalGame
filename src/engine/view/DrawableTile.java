package engine.view;

import engine.entities.GameObject;
import engine.entities.interfaces.IGameObject;
import engine.entities.composites.Sprite;
import engine.entities.items.Item;

public class DrawableTile {
    private IGameObject gameObject;
    private Item item;
    private Sprite sprite;

    public DrawableTile(IGameObject gameObject, Item item, Sprite sprite){
        this.gameObject = gameObject;
        this.item = item;
        this.sprite = sprite;

    }
    public DrawableTile(){};

    public void setGameAndItem(GameObject gameObject, Item item){
        this.gameObject = gameObject;
        this.item = item;
        this.sprite = new Sprite(1);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public IGameObject getGameObject() {
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
