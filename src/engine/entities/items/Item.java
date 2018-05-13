package engine.entities.items;

import engine.entities.gameobjects.Sprite;

import java.io.Serializable;

/**
 * Represents a item that a Player can pick up and/or use.
 */
public abstract class Item implements Serializable{

    private String name;
    private Sprite sprite;

    public Item(Sprite sprite){
        this.sprite = sprite;
    }
    public Item(Sprite sprite, String name){
        this.sprite = sprite;
        this.name = name;
    }

    public Sprite getSprite() {
        return sprite;
    }

}
