package engine.services.pathfinder;

import engine.world.Tile;
import engine.world.World;

import javax.naming.directory.InvalidAttributeValueException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class NodeMap {

    Node[][] nodes;
    private int worldWidth;

    public NodeMap(World world){
        worldWidth = world.getwidth();

        nodes = new Node[world.getwidth()][world.getHeight()];
        world.getWorld().parallelStream().forEach(tile -> nodes[axisTranslator(tile.getCordX())][axisTranslator(tile.getCordY())] = new Node(axisTranslator(tile.getCordX()), axisTranslator(tile.getCordY()),(tile.getGameObject() != null)));
    }

    public Node getNode(int x,  int y){
        if(x > -(worldWidth / 2) && x < (worldWidth / 2) && y > -(worldWidth / 2) && y < (worldWidth / 2)) {
            return nodes[axisTranslator(x)][axisTranslator(y)];
        }
        return null;
    }

    public Node getNodeByInternalPos(int x, int y){
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

    private int axisTranslator(int postion){
        return postion + (worldWidth / 2) ;
    }

}
