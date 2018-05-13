package engine.services.pathfinder;

import engine.entities.gameobjects.interfaces.Updatable;
import engine.world.World;

import java.util.*;
import java.util.concurrent.*;

public class PathSearchService implements Updatable {

    private ExecutorService searchPool;
    private World world;
    private List<PathRequest> requestList;
    private List<PathPair> pathPairs;
    private int counter;


    public PathSearchService(World world){
        this.world = world;
        searchPool = Executors.newFixedThreadPool(10);
        requestList = new ArrayList<>();
        pathPairs = new ArrayList<>();

    }

    public Optional<Future<Path>> getNewPath(int startX, int startY, int targetX, int targetY){

            if(requestList.size() < 50){
                CompletableFuture completableFuture = new CompletableFuture();
                PathRequest pathRequest = new PathRequest(completableFuture, startX, startY, targetX, targetY);
                requestList.add(pathRequest);
                return Optional.of(completableFuture);
            }
            else {
                System.out.println("Full list");
                return Optional.empty();
            }
    }


    public void update(){
        counter++;
        if(counter == 5){
            if(requestList.size() > 0){
                PathRequest request = requestList.remove(0);

                searchPool.submit(new Runnable() {
                    @Override
                    public void run(){
                        NodeMap nodeMap = new NodeMap(world);
                        Pathfinder pathfinder = new Pathfinder(100,nodeMap, new Heuristic());
                        request.future.complete(pathfinder.findPath(request.startX,request.startY,request.targetX,request.targetY));
                    }
                });
                System.out.println("New Path made");
            }
            counter = 0;
        }
    }

    @Override
    public boolean isDead() {
        return false;
    }

    private void sendNewPaths(){
        pathPairs.stream().filter(pathPair -> pathPair.pathFuture.isDone()).forEach(pathPair -> pathPair.receiver.receiveNewPath(pathPair.pathFuture));
        pathPairs.clear();
    }

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

    private class PathPair{
        private final Future<Path> pathFuture;
        private final AsyncPathReceiver receiver;

        public PathPair(Future<Path> pathFuture, AsyncPathReceiver receiver) {
            this.pathFuture = pathFuture;
            this.receiver = receiver;
        }
    }

    public void shutdown(){
        searchPool.shutdown();
        searchPool.shutdownNow();
    }

}
