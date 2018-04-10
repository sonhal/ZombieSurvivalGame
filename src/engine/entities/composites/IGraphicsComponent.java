package engine.entities.composites;

import java.util.ArrayList;

public interface IGraphicsComponent {

    Sprite getSprite();

    ArrayList<Sprite> getSpriteList();

    void addSprite(Sprite newSprite);

    void setActiveSpriteByID(int id);
}
