package engine.entities.components.ComponentEvent;

import engine.controllers.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CollisionEventTest {
    ComponentEventWrapper<CollisionEvent> event;

    @BeforeEach
    void SetUp(){

    }

    @Test
    void collisonDirection() {
        event = new ComponentEventWrapper<>(new CollisionEvent(Direction.UP));
        CollisionEvent test = event.getEvent();
        test.collisonDirection();
    }
}