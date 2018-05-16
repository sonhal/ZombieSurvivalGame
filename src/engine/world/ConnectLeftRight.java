package engine.world;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Class connecting each of the tiles in the x direction. As the design structure of the game allows us to have hundreds of thousands of tiles,
 * it is important that this connectiong algorithm is as effective as posible.
 * First this process sorts the list of every tile by its x cordinates making a long strip of one dimentional game.
 * Then it iterates through the list of sorted tiles, mapping the tiles which are located at the same row.
 */

class ConnectLeftRight implements Runnable {
    List<Tile> worldToBeConnected;
    Thread t;

    /**
     * Constructor spawning the thread
     * @param worldList Reference to the list of tiles representing the world.
     */
    ConnectLeftRight(List<Tile> worldList) {
        worldToBeConnected = new ArrayList(worldList);
        t = new Thread(this);
        t.start();
    }

    /** A compirator for sorting the tiles after each other in the horizontal plane.
     * Makes the whole process of connecting tiles extremely effective as they are all lined up.
     */

    Comparator<Tile> xDirectionalSorting = new Comparator<Tile>() {
        @Override
        public int compare(Tile t1, Tile t2) {
            if ((t1.getCordY() >= t2.getCordY() && (t1.getCordX() > t2.getCordX()) || t1.getCordY() > t2.getCordY())) {
                return 1;
            } else {
                return -1;
            }
        }
    };

    /**
     * Connecting tile.left and tile.right on each tile object.
     * This run method is a part of the runnable interface, and will automatically execute after this tread is created.
     *  1. Sorts the world list using the xDirectional Sorting operator.
     *  2. Iterates through each element in the sorted list of tiles.
     *  3. Connects left and right tiles using the last and current variables.
     *  3. Each time it reaches the end it maps the last tile with the first tile from each row, making this round earth effect.
     */
    public void run() {
        worldToBeConnected.sort(xDirectionalSorting);
        Iterator xIterator = worldToBeConnected.iterator();
        Tile last = null;
        Tile beginning = null;
        Tile current;
        if (xIterator.hasNext()) {
            last = beginning = (Tile) xIterator.next();
        }
        while (xIterator.hasNext()) {
             current = (Tile) xIterator.next();
            if (last != null && current != null && last.getCordX() == current.getCordX() - 1) {
                current.setLeft(last);
                last.setRight(current);
            }else if (current != null && last != null && last.getCordX() != current.getCordX() - 1){
                    beginning.setLeft(last);
                    last.setRight(beginning);
                    current.setLeft(last);
                    beginning = current;
            }
            last = current;
        }
        last.setRight(beginning);
    }
}
