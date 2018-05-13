package engine.services.pathfinder;

import engine.controllers.Direction;

import java.util.ArrayList;
import java.util.List;

public class Path {

    private List<int[]> path;
    private int[] lastPoint;

    public Path() {
        path = new ArrayList<>();
    }

    public void addStep(int x, int y){
        path.add(new int[] {x,y});
    }

    public int[] getStep(){
        try {
            int[] re = path.get(path.size() - 1);
            path.remove(path.size() - 1);
            return re;
        }catch (Exception er){
            return null;
        }

    }

    public Direction getNextStepDirection(){
        if(path.size() < 1){
            return null;
        }
        if(lastPoint == null){
            lastPoint = path.remove(0);
        }

        int[] nextPoint = path.remove(0);
        Direction direction = pointToPointDirection(lastPoint, nextPoint);
        lastPoint = nextPoint;
        return direction;

    }

    private Direction pointToPointDirection(int[] last, int[] next){
        int startX = last[0];
        int startY = last[1];

        int targetX = next[0];
        int targetY = next[1];

        if(startX == targetX){
            if (startY < targetY){
                return Direction.UP;
            }
            return Direction.DOWN;
        }
        else {
            if (startX < targetX){
                return Direction.LEFT;
            }
            return Direction.RIGHT;
        }
    }
}
