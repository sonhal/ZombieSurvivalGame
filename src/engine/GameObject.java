package engine;

import javafx.scene.control.Skin;


/**
 * Represents the abstract GameObject.
 * A GameObject subtype object can be on a Tile object.
 */
public abstract class GameObject {

    public Sprite sprite;
    public int health;

    public GameObject(Sprite sprite, int health){
        this.sprite = sprite;
        this.health = health;
    }



}
