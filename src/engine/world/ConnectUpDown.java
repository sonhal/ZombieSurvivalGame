package engine.world;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ConnectUpDown implements Runnable {

    List<Tile> worldToBeConnected;
    Thread t;

    /**
     * Constructor spawning the thread
     * @param worldList Reference to the list of tiles representing the world.
     */
    ConnectUpDown(List<Tile> worldList) {
        worldToBeConnected = new ArrayList(worldList);
        t = new Thread(this);
        t.start();
    }

    /** A compirator for sorting the tiles after each other in the vertical plane.
     * Makes the whole process of connecting tiles extremely effective as they are all lined up.
     */
    Comparator<Tile> yDirectionalSorting = new Comparator<Tile>() {
        @Override
        public int compare(Tile t1, Tile t2) {
            if (t1.getCordX() >= t2.getCordX() && (t1.getCordY() > t2.getCordY()) || t1.getCordX() > t2.getCordX()) {
                return 1;
            } else {
                return -1;
            }
        }
    };

    /**
     * Connecting tile.up and tile.down on each tile object.
     * This run method is a part of the runnable interface, and will automatically execute after this tread is created.
     *  1. Sorts the world list using the yDirectional Sorting operator.
     *  2. Iterates through each element in the sorted list of tiles.
     *  3. Connects up and down tiles using the last and current variables.
     *  3. Each time it reaches the end it maps the last tile with the first tile from each row, making this round earth effect.
     */
    public void run() {
        worldToBeConnected.sort(yDirectionalSorting);
        Iterator yIterator = worldToBeConnected.iterator();
        Tile last = null;
        Tile beginning = null;
        while (yIterator.hasNext()) {
            Tile current = (Tile) yIterator.next();
            if (beginning == null){ beginning = current;}
            if (last != null && current != null && last.getCordY() == current.getCordY() - 1) {
                current.setUp(last);
                last.setDown(current);
            }else{
                if (current != null && last != null && last.getCordY() != current.getCordY() - 1){
                    beginning.setUp(last);
                    last.setDown(beginning);
                    beginning = current;
                }
            }
            last = current;
        }
        last.setDown(beginning);
        beginning.setUp(last);
    }

}
