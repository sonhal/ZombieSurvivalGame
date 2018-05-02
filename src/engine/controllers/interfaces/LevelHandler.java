package engine.controllers.interfaces;

import java.io.Serializable;

public interface LevelHandler extends Serializable {

    void update(GameScore score);

    Level getCurrentLevel();

}
