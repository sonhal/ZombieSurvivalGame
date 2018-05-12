package engine.services.pathfinder;

import java.util.concurrent.Future;

public interface AsyncPathReceiver {

    void receiveNewPath(Future<Path> newPath);
}
