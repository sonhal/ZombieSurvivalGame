package engine.entities.components.ComponentEvent;

import engine.controllers.Direction;

public class CheckForCollisionEvent implements ComponentEvent{

    private final Direction direction;

    public CheckForCollisionEvent(final Direction checkDirection) {
        direction = checkDirection;
    }

    public Direction getDirection() {
        return direction;
    }
}
