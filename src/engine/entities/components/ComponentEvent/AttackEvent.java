package engine.entities.components.ComponentEvent;

import engine.controllers.Direction;
import engine.entities.components.AttackComponent;

public class AttackEvent implements ComponentEvent {

    private final Direction attackDirection;

    public AttackEvent(final Direction attackDirection){
        this.attackDirection = attackDirection;
    }

    public Direction getAttackDirection() {
        return attackDirection;
    }
}
