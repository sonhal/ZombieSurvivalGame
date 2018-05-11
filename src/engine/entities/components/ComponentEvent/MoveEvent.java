package engine.entities.components.ComponentEvent;

import engine.controllers.Direction;

public class MoveEvent implements ComponentEvent {

    private final Direction moveDirection;

    public MoveEvent(final Direction direction){
        this.moveDirection = direction;
    }

    public Direction getDirection(){
        return moveDirection;
    }
}
