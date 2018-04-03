package engine.view;

import engine.entities.GameObject;
import engine.entities.composites.Sprite;
import engine.entities.items.Item;

import java.util.Random;

public class DrawableTile {
    private GameObject gameObject;
    private Item item;
    private Sprite sprite;

    public DrawableTile(GameObject gameObject, Item item, Sprite sprite){
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
