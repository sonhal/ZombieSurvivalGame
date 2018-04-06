package engine.services;

public interface AStarHeuristic {
    int getCost(int targetX, int targetY, int currentX, int currentY);
}
