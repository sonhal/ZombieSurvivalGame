package engine.view;

import engine.entities.gameobjects.StaticGameObject;
import engine.entities.gameobjects.interfaces.GameObject;
import engine.entities.gameobjects.Sprite;
import engine.entities.items.Item;

import java.io.Serializable;

/**
 * Simplified version of the Tile object which only keeps the property's which is necessary to be drawn on the screen.
 * Doing it this way decouples the dependency between the tile and view.
 */
public class DrawableTile implements Serializable{
    private GameObject gameObject;
    private Item item;
    private Sprite sprite;
    private GameObject particleEffect;

    public DrawableTile(GameObject gameObject, Item item, Sprite sprite, GameObject particleEffect){
        this.gameObject = gameObject;
        this.item = item;
        this.sprite = sprite;
        this.particleEffect = particleEffect;

    }
    public DrawableTile(){};

    public void setGameAndItem(StaticGameObject gameObject, Item item){
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

    public void setGameObject(StaticGameObject gameObject) {
        this.gameObject = gameObject;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public GameObject getParticleEffect() {
        return particleEffect;
    }

}
