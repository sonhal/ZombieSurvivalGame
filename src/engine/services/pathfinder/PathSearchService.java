package engine.services.pathfinder;

import engine.entities.gameobjects.interfaces.Updatable;
import engine.world.World;

import java.util.*;
import java.util.concurrent.*;
/**
 * Responsible for new pathfinding requests and allocation of worker threads to new requests.
 * Having a single responsibility services handling the cadence of pathfinding calculation gives us the
 * possibility to fine tune stability in the game.
 *
 * Objects that wants a path from a point to another can call the getNewPath() method and if the request was succesfull
 * they will receive a CompletableFuture object.
 *
 * The update() method is called once every game loop iteration. if there are requests in the request list the
 * PathSearchService will remove the request from the list and submit the request to the thread pool and set the
 * requests CompletableFuture as the receiver of the Pathfinder search.
 */
public class PathSearchService implements Updatable {

    private ExecutorService searchPool;
    private World world;
    private List<PathRequest> requestList;
    private int counter;

    /**
     *
     * @param world reference to the world object. This needs to be passed to the NodeMap used by the Pathfinder
     * Constructs a thread pool to allocate new pathfinding request to.
     */
    public PathSearchService(World world){
        this.world = world;
        searchPool = Executors.newFixedThreadPool(10);
        requestList = new ArrayList<>();
    }

    /**
     * Call method for all Objects in the game that wants a best guess path to a coordinate from another.
     * It can only guaranty a best guess that will be in the direction of the target coordinate as the distance between
     * the from and to coordinates could be large enough to trigger the maxSearchDistance.
     * @param startX
     * @param startY
     * @param targetX
     * @param targetY
     * @return depending on if there was room in the request list the caller will receive a Optional Future.
     * If the Future is present the caller can expect the request to be processed.
     */
    public Optional<Future<Path>> getNewPath(int startX, int startY, int targetX, int targetY){

            if(requestList.size() < 50){
                CompletableFuture completableFuture = new CompletableFuture();
                PathRequest pathRequest = new PathRequest(completableFuture, startX, startY, targetX, targetY);
                requestList.add(pathRequest);
                return Optional.of(completableFuture);
            }
            else {
                return Optional.empty();
            }
    }

    /**
     * The update method
     */
    public void update(){
        counter++;
        if(counter == 5){
            System.out.println(requestList.size());
            if(requestList.size() > 0){
                PathRequest request = requestList.remove(0);
                searchPool.submit(new Runnable() {
                    @Override
                    public void run(){
                        NodeMap nodeMap = new NodeMap(world);
                        Pathfinder pathfinder = new Pathfinder(10,nodeMap, new ManhattanHeuristic());
                        request.future.complete(pathfinder
                                .findPath(request.startX,request.startY,request.targetX,request.targetY));
                    }
                });
            }
            counter = 0;
        }
    }

    @Override
    public boolean isDead() {
        return false;
    }

    /**
     * Data class holding the data for a given request received by the PathSearchService
     */
    private class PathRequest{
        public final int startX;
        public  final  int startY;
        public  final  int targetX;
        public  final int targetY;
        public final CompletableFuture<Path> future;

        public PathRequest(CompletableFuture<Path> completableFuture, int startX, int startY, int targetX, int targetY) {
            this.startX = startX;
            this.startY = startY;
            this.targetX = targetX;
            this.targetY = targetY;
            this.future = completableFuture;
        }
    }


    public void shutdown(){
        searchPool.shutdown();
        searchPool.shutdownNow();
    }

}
