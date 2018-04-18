package engine.entities.composites;

import java.util.ArrayList;
import java.util.Queue;

public interface IGraphicsComponent {

    Queue<Sprite> getSprite();

    ArrayList<Sprite> getSpriteList();

    void addSprite(Sprite newSprite);

    void setActiveSpriteByID(int id);
}
