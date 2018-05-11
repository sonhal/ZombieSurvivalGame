package engine.services.pathfinder;

import engine.world.World;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PathSearchService {

    private ExecutorService searchPool;
    private World world;


    public PathSearchService(World world){
        this.world = world;
        searchPool = Executors.newFixedThreadPool(10);
    }

    public Future<Path> getNewPath(int startX, int startY, int targetX, int targetY){
        return searchPool.submit(new Callable<Path>() {
                @Override
                public Path call() throws Exception {
                    NodeMap nodeMap = new NodeMap(world);
                    Pathfinder pathfinder = new Pathfinder(100,nodeMap, new Heuristic());
                    return pathfinder.findPath(startX,startY,targetX,targetY);
                }
            });
    }

}
