package engine.entities.components;

import engine.entities.components.ComponentEvent.ComponentEvent;
import engine.entities.components.ComponentEvent.DeathEvent;
import engine.entities.components.interfaces.GraphicsComponent;
import engine.entities.gameobjects.Sprite;
import engine.entities.gameobjects.UpdatableGameObject;
import engine.entities.gameobjects.interfaces.GameObject;
import engine.entities.gameobjects.interfaces.IUpdatableGameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Graphical component that runs an animation and then kills the GameObject.
 */
public class OneUseUpdatableGraphicsComponent extends GraphicsComponent {

    private List<Sprite>  sprites;
    private int updateDelay;
    private Sprite activeSprite;
    private double lastSpriteUpdate;

    public OneUseUpdatableGraphicsComponent(List<Sprite> sprites, int updateDelay) {
        this.sprites = sprites;
        this.updateDelay = updateDelay;
        this.activeSprite = sprites.remove(0);
        lastSpriteUpdate = System.currentTimeMillis();
    }

    @Override
    public Iterable<Sprite> getActiveSprites() {
        List<Sprite> sprites = new ArrayList<>();
        sprites.add(activeSprite);
        return sprites;
    }

    @Override
    public List<Sprite> getSpriteList() {
        return sprites;
    }

    @Override
    public void addSprite(Sprite newSprite) {
        if(newSprite != null){
            sprites.add(newSprite);
        }
    }

    @Override
    public void setActiveSpriteByID(int id) {
        //Do nothing
    }

    @Override
    public void update(GameObject gameObject) {
        if(canActivate(updateDelay, lastSpriteUpdate)){
            if(sprites.size() > 0){
                activeSprite = sprites.remove(0);
            }
            else {
                System.out.println("Send Death event");
                sendMessageToAllComponents(gameObject.getComponents(), new DeathEvent());
                if(gameObject instanceof IUpdatableGameObject){
                    UpdatableGameObject uo = (UpdatableGameObject)gameObject;
                    uo.die();
                }
            }
            lastSpriteUpdate = System.currentTimeMillis();
        }
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
        //Do nothing
    }
}
