package engine.controllers.interfaces;

import engine.entities.interfaces.IGameObject;

public interface GameScore {

    Score getPlayerScore();

    void update(IGameObject gameObject);


}
