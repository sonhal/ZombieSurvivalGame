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
        Heuristic heuristic = new Heuristic();
        pathfinder = new Pathfinder(17, nodeMap, heuristic);
    }

    @Test
    void findPath() {
        Path path = pathfinder.findPath(1,1,2,3);
        assertNotNull(path);
        int[] step = path.getStep();
        while (step != null){
            System.out.println(step[0] + " " + step[1]);
            step = path.getStep();
        }
        Path path2 = pathfinder.findPath(1,1,2,3);
        assertNotNull(path2);
        Direction step2 = path2.getNextStepDirection();
        while (step2 != null){
            System.out.println(step2);
            step2 = path2.getNextStepDirection();
        }

    }
}