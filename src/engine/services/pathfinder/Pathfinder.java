package engine.services.pathfinder;


import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * A* pathfinder implementation for this game.
 * This implementations open list uses TreeSet as its data structure as this gives us O(log n)
 * insertion and since this algorithm retrieves the top element alot during the calculation the
 * O(1) retrieval of the first element seemed favourable.
 *
 * Note that it takes a max search distance in its constructor. This value determines how far from the enemy the
 * Pathfinder will search before it gives up. In that case it will return its best guess.
 */
public class Pathfinder {

    private TreeSet<Node> openList;
    private List<Node> closedList;
    private int maxSearchDistance;
    private ManhattanHeuristic heuristic;
    private NodeMap nodes;

    public Pathfinder(int maxSearchDistance, NodeMap worldMap, ManhattanHeuristic heuristic){
        this.maxSearchDistance = maxSearchDistance;
        this.heuristic = heuristic;
        openList = new TreeSet<>();
        closedList = new ArrayList<>();
        this.nodes = worldMap;

    }

    /**
     * Takes the start and target coordinates and will search for a path to the target util it is found,
     * it reaches the max depth set in the constructor or there are no more nodes to search.
     *
     * If the max depth is reached it will return the path to the Node in current open list with the best cost.
     * The Pathfinder is used to find a path from a enemy to the player. The player can change position rapidly so if
     * the distance between the player and enemy is large its wasteful to calculate the complete path as it most likely
     * needs to be recalculated before the enemy reaches the end of the path
     * @param startX
     * @param startY
     * @param targetX
     * @param targetY
     * @return the complete path or its best guess at the best path.
     */
    public Path findPath(int startX, int startY, int targetX, int targetY){
        int convertedStartX = nodes.pointConverter(startX);
        int convertedStartY = nodes.pointConverter(startY);
        int convertedTargetX = nodes.pointConverter(targetX);
        int convertedTargetY = nodes.pointConverter(targetY);


        nodes.getNodeByTranslatedCoordinates(convertedStartX,convertedStartY).cost = 0;
        nodes.getNodeByTranslatedCoordinates(convertedStartX, convertedStartY).depth = 0;
        nodes.getNodeByTranslatedCoordinates(convertedTargetX,convertedTargetY).isBlocked = false;

        closedList.clear();
        openList.clear();
        openList.add(nodes.getNodeByTranslatedCoordinates(convertedStartX, convertedStartY));

        nodes.getNodeByTranslatedCoordinates(convertedTargetX, convertedTargetY).parent = null;

        int maxDepth = 0;
        Node current;
        int currentX;
        int currentY;
        float currentCost;
        while ((openList.size() != 0) && (maxDepth < maxSearchDistance)){
            current = getFirstInOpen();
            currentX = current.x;
            currentY = current.y;
            currentCost = current.cost;
            if(current == nodes.getNodeByTranslatedCoordinates(convertedTargetX, convertedTargetY)){
                break;
            }
            removeFromOpen(current);
            addToClosed(current);

            for(int x=-1; x < 2; x++){
                for(int y = -1; y < 2; y++){
                    if ((x == 0) && (y == 0)){
                        continue;
                    }
                    if ((x != 0) && (y != 0)) {
                        continue;
                    }

                    int xPoint = x + currentX;
                    int yPoint = y + currentY;

                    if(isValidLocation(convertedStartX , convertedStartY, xPoint, yPoint)){
                        float nextStepCost = currentCost + getMovementCost(currentX, currentY, xPoint, yPoint);
                        Node neighbour = nodes.getNodeByTranslatedCoordinates(xPoint, yPoint);
                        if(nextStepCost < neighbour.cost) {
                            if(inOpenList(neighbour)){
                                removeFromOpen(neighbour);
                            }
                            if(inClosedList(neighbour)){
                                removeFromClosed(neighbour);
                            }
                        }

                        if(!inOpenList(neighbour) && !(inClosedList(neighbour))){
                            neighbour.cost = nextStepCost;
                            maxDepth = Math.max(maxDepth, neighbour.setParent(current));
                            neighbour.heuristic = getHeuristicCost(xPoint, yPoint, convertedTargetX, convertedTargetY);
                            addToOpen(neighbour);
                        }

                    }



                }
            }


        }
        //If we could not find a path we try the best guess
        if(nodes.getNodeByTranslatedCoordinates(convertedTargetX, convertedTargetY).parent == null){
            Node target = openList.pollFirst();
            return createPath(convertedStartX, convertedStartY, target);
        }
        //We have found a path to the target location
        Node target = nodes.getNodeByTranslatedCoordinates(convertedTargetX, convertedTargetY);
        return createPath(convertedStartX, convertedStartY, target);
    }

    protected Path createPath(int convertedStartX, int convertedStartY, Node target){
        if(target != null){
            Path path = new Path();
            while (target != nodes.getNodeByTranslatedCoordinates(convertedStartX, convertedStartY)){
                path.addStep(target.x, target.y);
                target = target.parent;
            }
            path.addStep(convertedStartX, convertedStartY);
            return path;
        }
        else {
            throw new NullPointerException("Node parameter to method was null");
        }
    }

    protected Node getFirstInOpen() {
        return openList.pollFirst();
    }
    protected void addToOpen(Node node) {
        openList.add(node);
    }

    protected boolean inOpenList(Node node) {
        return openList.contains(node);
    }
    protected void removeFromOpen(Node node) {
        openList.remove(node);
    }
    protected void addToClosed(Node node) {
        closedList.add(node);
    }
    protected boolean inClosedList(Node node) {
        return closedList.contains(node);
    }
    protected void removeFromClosed(Node node) {
        closedList.remove(node);
    }
    protected boolean isValidLocation(int startX, int startY, int searchX, int searchY){
        boolean isInvalid = (nodes.getNodeByTranslatedCoordinates(searchX, searchY) == null);
        if((!isInvalid) && ((startX != searchX) || (startY != searchY))){
            isInvalid = nodes.isBlocked(searchX, searchY);
        }
        return !isInvalid;
    }

    public float getMovementCost(int sx, int sy, int tx, int ty) {
        return nodes.getCost(sx, sy, tx, ty);
    }
    public float getHeuristicCost(int x, int y, int tx, int ty) {
        return heuristic.getCost(x, y, tx, ty);
    }

}
