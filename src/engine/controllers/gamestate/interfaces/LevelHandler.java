package engine.controllers.gamestate.interfaces;

import java.io.Serializable;

public interface LevelHandler extends Serializable {

    void update(GameScore score);

}
