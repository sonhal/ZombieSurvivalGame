package engine.controllers.gamestate.interfaces;

import java.io.Serializable;

public interface StateKeeper extends Serializable{

    void update(Object message);


}
