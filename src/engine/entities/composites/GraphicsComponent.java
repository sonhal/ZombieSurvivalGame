package engine.entities.composites;

import engine.entities.interfaces.IGameObject;

import java.io.Serializable;
import java.util.ArrayList;

public class GraphicsComponent implements IGraphicsComponent, Component<IGameObject>, Serializable{


    private ArrayList<Sprite> sprites;
    private Sprite activeSprite;

    public GraphicsComponent(Sprite sprite){
        this.sprites = new ArrayList<>();
        this.sprites.add(sprite);
        this.activeSprite = this.sprites.get(0);
    }

    public Sprite getSprite() {
        return activeSprite;
    }

    public ArrayList<Sprite> getSpriteList(){
        return sprites;
    }

    public void addSprite(Sprite newSprite){
        this.sprites.add(newSprite);
    }

    public void setActiveSpriteByID(int id) {
        this.activeSprite = sprites.get(id);
    }


    @Override
    public void update(IGameObject componentHolder) {
        //Do nothing
    }
}
