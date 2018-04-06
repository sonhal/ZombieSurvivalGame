package engine.services.pathfinder;

public interface AStarHeuristic {
    int getCost(int targetX, int targetY, int currentX, int currentY);
}
