package engine.services.pathfinder;

public class ManhattanHeuristic implements AStarHeuristic {

    public int getCost(int targetX, int targetY, int currentX, int currentY){
        int distanceX = targetX - currentX;
        int distanceY = targetY - currentY;
        return  distanceX + distanceY;
    }
}
