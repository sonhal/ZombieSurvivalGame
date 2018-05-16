package engine.entities.components;

import engine.entities.components.ComponentEvent.ComponentEvent;
import engine.entities.gameobjects.Sprite;
import engine.entities.components.interfaces.GraphicsComponent;
import engine.entities.gameobjects.interfaces.GameObject;

import java.util.ArrayList;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * StaticGraphicsComponent is to be used when the Graphics does not need to change with updates.
 */
public class StaticGraphicsComponent extends GraphicsComponent {
    private Queue<Sprite> activeSprites;
    private ArrayList<Sprite> sprites;

    public StaticGraphicsComponent(Sprite sprite){
        this.activeSprites = new ArrayBlockingQueue<>(3);
        this.sprites = new ArrayList<>();
        this.sprites.add(sprite);
        activeSprites.add(sprites.get(0));
    }

    @Override
    public Iterable<Sprite> getActiveSprites() {
        return activeSprites;
    }

    @Override
    public List<Sprite> getSpriteList(){
        return sprites;
    }

    public void addSprite(Sprite newSprite){
        this.sprites.add(newSprite);
    }

    public void setActiveSpriteByID(int id) {
        this.activeSprites.clear();
        this.activeSprites.add(sprites.get(id));
    }


    @Override
    public void update(GameObject componentHolder) {
        //Do nothing
    }

    @Override
    public void handle(ComponentEvent event) {
        //Do nothing
    }

    @Override
    public void innit(GameObject gameObject) {
        //Do nothing
    }

    @Override
    public void cleanUp(GameObject gameObject) {

    }
}
