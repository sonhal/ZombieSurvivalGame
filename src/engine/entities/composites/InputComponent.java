package engine.entities.composites;


import engine.controllers.Direction;
import engine.entities.Avatar;

public interface InputComponent {

    void update(Avatar avatar);
    Direction getMoveEvent();
    Direction getAttackEvent();
}
