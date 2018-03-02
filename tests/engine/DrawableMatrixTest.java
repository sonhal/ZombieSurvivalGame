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
        testWorld = new World(60);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void generateDrawable() {
        int diameterSize = 30;
        testWorld.findTile(0,0).setItem(new Item());
        testWorld.findTile(5,5).setItem(new Item());
        matrix = new DrawableMatrix(testWorld, testWorld.seed, diameterSize,diameterSize);
        
        assertNotNull(matrix.matrix[diameterSize][diameterSize].getItem());
        assertNotNull(matrix.matrix[diameterSize+5][diameterSize-5].getItem());
    }

    @Test
    void convertTile() {
    }

}