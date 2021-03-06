package engine.services.pathfinder;

/**
 * Represents one tile in the game World. Used in the Pathfinder to calculate paths.
 */
public class Node implements Comparable {


    public int x;
    public int y;
    public float cost;
    public Node parent;
    public float heuristic;
    public int depth;
    public boolean isBlocked;

    public Node(int x, int y, boolean isBlocked){
        this.x = x;
        this.y = y;
        this.isBlocked = isBlocked;
    }

    public int setParent(Node parent){
        depth = parent.depth + 1;
        this.parent = parent;
        return depth;
    }

    @Override
    public int compareTo(Object other) {
        Node o = (Node) other;

        float f = heuristic + cost;
        float of = o.heuristic + o.cost;

        if (f < of) {
            return -1;
        } else if (f > of) {
            return 1;
        } else {
            return 0;
        }
    }

    public boolean isBlocked(){
        return isBlocked;
    }

    @Override
    public String toString() {
        return "Node@ x: "+ x + " y: " + y;
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(x +""+ y);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Node)){
            return false;
        }
        Node node = (Node)obj;
        return ((x == node.x) && (y == node.y));
    }
}
