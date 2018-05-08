package engine.entities.components.interfaces;

import engine.entities.gameobjects.Sprite;

import java.util.List;

public interface IGraphicsComponent {

    Iterable<Sprite> getActiveSprites();

    List<Sprite> getSpriteList();

    void addSprite(Sprite newSprite);

    void setActiveSpriteByID(int id);
}
