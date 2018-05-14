package engine.services.pathfinder;

import engine.controllers.Direction;
import engine.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PathfinderTest {

    World world;
    Pathfinder pathfinder;
    NodeMap nodeMap;

    @BeforeEach
    void setUp() {
        world = new World();
        world.createNewGameWorld(50);
        nodeMap = new NodeMap(world);
        ManhattanHeuristic heuristic = new ManhattanHeuristic();
        pathfinder = new Pathfinder(100, nodeMap, heuristic);
    }

    @Test
    void findPath() {
        Path path = pathfinder.findPath(1,1,-40,-40);
        assertNotNull(path);
        Direction direction = path.getNextStepDirection();
        while (direction != null){
            System.out.println(direction);
            direction = path.getNextStepDirection();
        }
        pathfinder = new Pathfinder(100, nodeMap, new ManhattanHeuristic());
        Path path2 = pathfinder.findPath(1,1,-40,-40);
        int[] point = path2.getNextStepCoordinates();
        while (point != null){
            System.out.println(point[0] + " " + point[1]);
            point = path2.getNextStepCoordinates();
        }
    }

    @Test
    void findNegativePath(){
        Path path = pathfinder.findPath(1,1,-40,-40);
        assertNotNull(path);
    }

    @Test
    void findPositivePath(){
        Path path = pathfinder.findPath(1,1,40,40);
        assertNotNull(path);
    }

    @Test
    void findPositiveNegativePath(){
        Path path = pathfinder.findPath(1,1,-40,40);
        assertNotNull(path);
        pathfinder = new Pathfinder(100, nodeMap, new ManhattanHeuristic());
        path = pathfinder.findPath(1,1,40,-40);
        assertNotNull(path);
        pathfinder = new Pathfinder(100, nodeMap, new ManhattanHeuristic());
        path = pathfinder.findPath(1,-1,40,40);
        assertNotNull(path);
        pathfinder = new Pathfinder(100, nodeMap, new ManhattanHeuristic());
        path = pathfinder.findPath(-1,1,40,40);
        assertNotNull(path);
    }
}