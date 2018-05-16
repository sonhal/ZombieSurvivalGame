package engine.services.pathfinder;

import engine.world.World;


/**
 * Translates the Game world into a representation usable by the Pathfinder.
 * Translates the -/+ coordinates from the game World into only positive coordinates so we can use
 * an array for optimal query speed while calculating the path.
 */
public class NodeMap {

    private Node[][] nodes;
    private int worldWidth;

    public NodeMap(World world){
        //Assumes the world is a square.
        worldWidth = world.getWidth();

        nodes = new Node[world.getWidth()][world.getHeight()];
        world.getWorld().parallelStream().forEach(tile -> nodes[coordinateTranslator(tile.getCordX())][coordinateTranslator(tile.getCordY())] = new Node(coordinateTranslator(tile.getCordX()), coordinateTranslator(tile.getCordY()),(tile.getGameObject() != null)));
    }

    public Node getNodeByGameWorldCoordinates(int x, int y){
        if(x > -(worldWidth / 2) && x < (worldWidth / 2) && y > -(worldWidth / 2) && y < (worldWidth / 2)) {
            return nodes[coordinateTranslator(x)][coordinateTranslator(y)];
        }
        return null;
    }

    public Node getNodeByTranslatedCoordinates(int x, int y){
        if(x > 0 && x < worldWidth && y > 0 && y < worldWidth){
            return nodes[x][y];
        }
        return null;
    }

    public boolean isBlocked(int x, int y){
        return nodes[x][y].isBlocked();
    }

    public int getCost(int sx, int sy, int tx, int ty){
        return 1;
    }

    private int coordinateTranslator(int position){
        return position + (worldWidth / 2) ;
    }

    public int pointConverter(int postion){
        return postion + (worldWidth / 2) ;
    }

}
