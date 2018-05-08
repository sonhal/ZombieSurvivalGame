package engine.entities.components.ComponentEvent;

import engine.controllers.Direction;

public class CollisionEvent implements ComponentEvent {

    private final Direction direction;

    public CollisionEvent(final Direction collisionDirection){
        direction = collisionDirection;
    }

    public Direction collisonDirection() {
        return direction;
    }

}
