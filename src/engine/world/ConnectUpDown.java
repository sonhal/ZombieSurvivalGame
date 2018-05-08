package engine.world;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ConnectUpDown implements Runnable {

    List<Tile> worldToBeConnected;
    Thread t;

    ConnectUpDown(List<Tile> worldList) {
        worldToBeConnected = new ArrayList(worldList);
        t = new Thread(this);
        t.start();
    }


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


    public void run() {
        worldToBeConnected.sort(yDirectionalSorting);
        Iterator yIterator = worldToBeConnected.iterator();
        Tile last = null;
        while (yIterator.hasNext()) {
            Tile current = (Tile) yIterator.next();
            if (last != null && current != null && last.getCordY() == current.getCordY() - 1) {
                current.setUp(last);
                last.setDown(current);
            }
            last = current;
        }
    }

}
