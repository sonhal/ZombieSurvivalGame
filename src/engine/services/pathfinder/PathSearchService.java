package engine.services.pathfinder;

import engine.entities.gameobjects.interfaces.Updatable;
import engine.world.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    public void getNewPath(AsyncPathReceiver receiver, int startX, int startY, int targetX, int targetY){

        if(!requestList.contains(receiver)){
            if(requestList.size() < 50){
                PathRequest pathRequest = new PathRequest(receiver, startX, startY, targetX, targetY);
                requestList.add(pathRequest);
            }
            else {
                System.out.println("Full list");
            }
        }
    }


    public void update(){
        sendNewPaths();
        counter++;
        if(counter == 5){
            if(requestList.size() > 0){
                PathRequest request = requestList.remove(0);

                pathPairs.add(new PathPair(searchPool.submit(new Callable<Path>() {
                    @Override
                    public Path call() throws Exception {
                        NodeMap nodeMap = new NodeMap(world);
                        Pathfinder pathfinder = new Pathfinder(100,nodeMap, new Heuristic());
                        return pathfinder.findPath(request.startX,request.startY,request.targetX,request.targetY);
                    }
                }),request.receiver));
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
        public final AsyncPathReceiver receiver;

        public PathRequest(AsyncPathReceiver receiver, int startX, int startY, int targetX, int targetY) {
            this.startX = startX;
            this.startY = startY;
            this.targetX = targetX;
            this.targetY = targetY;
            this.receiver = receiver;
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
