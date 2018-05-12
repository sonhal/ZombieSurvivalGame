package engine.services.pathfinder;


import engine.world.World;

import java.util.ArrayList;
import java.util.List;

public class Pathfinder {

    private List<Node> openList;
    private SortedList closedList;
    private int maxSearchDistance;
    Heuristic heuristic;
    private NodeMap nodes;
    private Node currentDeepest;


    public Pathfinder(int maxSearchDistance, NodeMap worldMap, Heuristic heuristic){
        this.maxSearchDistance = maxSearchDistance;
        this.heuristic = heuristic;
        openList = new ArrayList<>();
        closedList = new SortedList();
        this.nodes = worldMap;

    }

    public Path findPath(int startX, int startY, int targetX, int targetY){
        nodes.getNode(startX,startY).cost = 0;
        nodes.getNode(startX, startY).depth = 0;
        nodes.getNode(targetX,targetY).isBlocked = false;
        closedList.clear();
        openList.clear();
        openList.add(nodes.getNode(startX, startY));

        nodes.getNode(targetX, targetY).parent = null;

        int maxDepth = 0;
        while ((openList.size() != 0) && (maxDepth < maxSearchDistance)){
            Node current = getFirstInOpen();
            if(current == nodes.getNode(targetX, targetY)){
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

                    int xPoint = x + current.x;
                    int yPoint = y + current.y;

                    if(isValidLocation(nodes.getNode(startX, startY).x,nodes.getNode(startX, startY).y, xPoint, yPoint)){
                        float nextStepCost = current.cost + getMovementCost(current.x, current.y, xPoint, yPoint);
                        Node neighbour = nodes.getNodeByInternalPos(xPoint, yPoint);
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
                            neighbour.heuristic = getHeuristicCost(xPoint, yPoint, nodes.getNode(targetX, targetY).x, nodes.getNode(targetX, targetY).y);
                            addToOpen(neighbour);
                            currentDeepest = neighbour;
                        }

                    }



                }
            }


        }
        if(nodes.getNode(targetX, targetY).parent == null){
            Path path = new Path();
            Node target = currentDeepest;
            while (target != nodes.getNode(startX, startY)){
                path.addStep(target.x, target.y);
                target = target.parent;
            }
            path.addStep(nodes.getNode(startX,startY).x, nodes.getNode(startX,startY).y);
            return path;
        }
        Path path = new Path();
        Node target = nodes.getNode(targetX, targetY);
        while (target != nodes.getNode(startX, startY)){
            path.addStep(target.x, target.y);
            target = target.parent;
        }
        path.addStep(nodes.getNode(startX,startY).x, nodes.getNode(startX,startY).y);
        return path;
    }

    protected Node getFirstInOpen() {
        return openList.get(0);
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
        boolean isInvalid = (nodes.getNodeByInternalPos(searchX, searchY) == null);
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
