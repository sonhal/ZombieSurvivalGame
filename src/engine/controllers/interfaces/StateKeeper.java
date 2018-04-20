package engine.controllers.interfaces;

import java.io.Serializable;

public interface StateKeeper extends Serializable{

    void update(Object message);


}
