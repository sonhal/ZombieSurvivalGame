package engine.services.pathfinder;

import static java.lang.Math.abs;


/**
 * Heuristic used in calculating the proximity of a coordinate to another.
 * As this game uses 4 direction movement in the Tile grid, this heuristic provides acquit precision.
 */
public class ManhattanHeuristic {

    public int getCost(int currentX, int currentY, int targetX, int targetY){
        return  abs(currentX - targetX) + abs(currentY - targetY);
    }
}
