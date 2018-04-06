package engine.entities.items;

import engine.entities.GameObject;
import engine.entities.composites.Sprite;
import engine.entities.items.weapons.Weapon;

/**
 * Represents a item that a Player can pick up and/or use.
 */
public abstract class Item {

    private Sprite sprite;

    public Item(Sprite sprite){
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public abstract Object pickup();
}
