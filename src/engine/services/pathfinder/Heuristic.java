package engine.services.pathfinder;

public class Heuristic {
    public int getCost(int currentX, int currentY, int targetX, int targetY){
        int distanceX = targetX - currentX;
        int distanceY = targetY - currentY;
        return  distanceX + distanceY;
    }
}
