package engine.entities.composites;

import engine.entities.interfaces.IGameObject;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class GraphicsComponent extends ScriptableComponent implements IGraphicsComponent{


    private ArrayList<Sprite> sprites;
    private Queue<Sprite> activeSprites;

    public GraphicsComponent(Sprite sprite){
        super(ComponentType.GRAPHICS_COMPONENT);
        this.sprites = new ArrayList<>();
        this.sprites.add(sprite);
        this.activeSprites = new ArrayBlockingQueue<Sprite>(3);
        this.activeSprites.add(this.sprites.get(0));
    }

    public Queue<Sprite> getSprite() {

        return activeSprites;
    }

    public ArrayList<Sprite> getSpriteList(){
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
    public void update(IGameObject componentHolder) {
        //Do nothing
    }

    @Override
    public void handle(Message message) {
        //Do nothing
    }

    @Override
    public void innit(IGameObject gameObject) {
        //Do nothing
    }

    @Override
    public void cleanUp(IGameObject gameObject) {

    }
}
