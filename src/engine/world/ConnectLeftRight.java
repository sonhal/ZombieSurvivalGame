package engine.world;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Class connecting each of the tiles in the x direction. As the design structure of the game allows us to have hundreds of thousands of tiles, it is important that this connectiong algorytm is as effective as posible.
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

    public void run() {
        worldToBeConnected.sort(xDirectionalSorting);
        Iterator xIterator = worldToBeConnected.iterator();
        Tile last = null;
        Tile beginning = null;
        if (xIterator.hasNext()) {
            beginning = (Tile) xIterator.next();
        }
        while (xIterator.hasNext()) {
            Tile current = (Tile) xIterator.next();
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
