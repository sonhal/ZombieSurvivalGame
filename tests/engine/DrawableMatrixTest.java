package engine;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;

import static org.junit.jupiter.api.Assertions.*;

class DrawableMatrixTest {
    DrawableMatrix matrix;
    World testWorld;

    @BeforeEach
    void setUp() {
        testWorld = new World(50);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void generateDrawable() {
        testWorld.findTile(2,3).right.setGameObject(new Weapon(100));
        matrix = new DrawableMatrix(testWorld, testWorld.seed,10);
        System.out.println("--------------------------------------------------------------");
        for(int y = 0; y< 10;y++){
            for(int x = 0; x< 10;x++){
                System.out.println("(" + x + " " + y + ") - " +matrix.drawableMatrix[x][y].getPos());
            }
        }


    }

    @Test
    void convertTile() {
    }

}