package engine.entities.components.interfaces;

import engine.entities.components.ScriptableComponent;
import engine.entities.gameobjects.Sprite;

import java.util.List;

public abstract class GraphicsComponent extends ScriptableComponent {

    public abstract Iterable<Sprite> getActiveSprites();

    public abstract List<Sprite> getSpriteList();

    public abstract void addSprite(Sprite newSprite);

    public abstract void setActiveSpriteByID(int id);
}
