package engine.services.pathfinder;

import engine.controllers.Direction;
import engine.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

class PathSearchServiceTest {

    World world;
    PathSearchService pathSearchService;
    Future<Path> pathFuture;

    @BeforeEach
    void setUp() {
        world = new World();
        world.createNewGameWorld(10);
        pathSearchService = new PathSearchService(world);
    }

    @Test
    void getNewPath() {
        Optional optional = pathSearchService.getNewPath(3,3,10,15);
        pathSearchService.update();
        pathSearchService.update();
        pathSearchService.update();
        pathSearchService.update();
        pathSearchService.update();
        pathSearchService.update();

        pathFuture = (Future<Path>) optional.get();
        while (!(pathFuture.isDone())){
            continue;
        }
        try {
            Path path = pathFuture.get();
            Direction direction = path.getNextStepDirection();
            while (direction != null){
                System.out.println(direction);
                direction = path.getNextStepDirection();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Test
    void update() {
    }

    @Test
    void shutdown() {
    }
}