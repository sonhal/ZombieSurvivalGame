package engine.entities.composites;


import engine.controllers.Direction;
import engine.entities.Avatar;

import java.io.Serializable;

public interface InputComponent extends Serializable{

    void update(Avatar avatar);
    Direction getMoveEvent();
    Direction getAttackEvent();
}
