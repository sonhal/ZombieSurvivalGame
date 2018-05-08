package engine.world;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

class ConnectLeftRight implements Runnable {
    List<Tile> worldToBeConnected;
    Thread t;

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
        while (xIterator.hasNext()) {
            Tile current = (Tile) xIterator.next();
            if (last != null && current != null && last.getCordX() == current.getCordX() - 1) {
                current.setLeft(last);
                last.setRight(current);
            }
            last = current;
        }
    }
}
