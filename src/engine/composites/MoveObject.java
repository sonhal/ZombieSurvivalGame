package engine.composites;

import engine.Direction;

public class MoveObject {

    private Direction direction;
    private int range;

    public MoveObject(Direction direction, int range){
        this.direction = direction;
        this.range = range;
    }
}
