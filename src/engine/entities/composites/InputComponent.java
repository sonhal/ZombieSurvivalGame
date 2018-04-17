package engine.entities.composites;


import engine.controllers.Direction;

public interface InputComponent {

    Direction getMoveEvent();
    Direction getAttackEvent();
}
