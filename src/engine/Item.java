package engine;

import engine.composites.Sprite;

/**
 * Represents a item that a player Avatar can pick up and/or use.
 */
public class Item{

    Sprite sprite;

    public Item(Sprite sprite){
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Weapon pickup(){
        return null;
    }
}
