package engine;

import engine.composites.Sprite;
import javafx.scene.control.Skin;


/**
 * Represents the abstract GameObject.
 * A GameObject subtype object can be on a Tile object.
 */
public abstract class GameObject {

    public Skin skin;
    public Sprite sprite;
    public int health;

    public GameObject(Sprite sprite, int health){
        this.sprite = sprite;
        this.health = health;
    }

    public Skin getSkin() {
        return skin;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void move(Tile tile){

    }
}
