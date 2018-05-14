package engine.services.pathfinder;

import engine.controllers.Direction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A path a GameObject can use to direct it through the game world.
 * the GameObject can get the path data as a in[] containing the x, y values of the next Node in the path or
 * as Directions from the last til to the next in the Path.
 */
public class Path implements Serializable {

    private List<int[]> path;
    private int[] lastPoint;

    public Path() {
        path = new ArrayList<>();
    }

    public void addStep(int x, int y){
        path.add(new int[] {x,y});
    }

    /**
     * Returns the next step in the path as a int array with the x y values of the next Node in the path
     * @return the next Node
     */
    public int[] getNextStepCoordinates(){
        try {
            int[] re = path.get(path.size() - 1);
            path.remove(path.size() - 1);
            return re;
        }catch (Exception er){
            return null;
        }
    }

    /**
     * Returns the next step in the Path as a Direction
     */
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

    /**
     * Finds the Direction from one xy Node to another. This assumes the nodes ar Horizontal or Vertically aligned.
     * If it cannot find the direction it returns null;
     *
     * @param last the Directional from point
     * @param next the point the Direction points to
     * @return the Direction from last to next
     */
    private Direction pointToPointDirection(int[] last, int[] next){
        int startX = last[0];
        int startY = last[1];

        int targetX = next[0];
        int targetY = next[1];

        if(startX == targetX){
            if (startY < targetY){
                return Direction.UP;
            }
            else {
                return Direction.DOWN;
            }
        }
        if(startY == targetY){
            if (startX < targetX){
                return Direction.LEFT;
            }
            return Direction.RIGHT;
        }
        return null;
    }
}
