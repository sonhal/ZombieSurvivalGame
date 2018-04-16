package engine.entities.composites;


import engine.controllers.Direction;
import engine.entities.Avatar;

import java.io.Serializable;

public interface InputComponent {

    Direction getMoveEvent();
    Direction getAttackEvent();
}
