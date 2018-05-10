package engine.services.pathfinder;

import engine.controllers.Direction;

import java.util.ArrayList;
import java.util.List;

public class Path {

    private List<int[]> path;

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
        if(path.size() < 2){
            return null;
        }
        int[] point = path.remove(0);
        int[] nextPoint = path.remove(0);
        return pointToPointDirection(point, nextPoint);

    }

    private Direction pointToPointDirection(int[] start, int[] target){
        int startX = start[0];
        int startY = start[1];

        int targetX = target[0];
        int targetY = target[1];

        if(startX == targetX){
            if(startY < targetY){
                return Direction.UP;
            }
            return Direction.DOWN;
        }
        if(startX < targetX){
            return Direction.LEFT;
        }
        return Direction.RIGHT;
    }
}
