package engine;

import engine.composites.Sprite;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        testWorld.findTile(0,0).setItem(new Item(new Sprite(1)));
        testWorld.findTile(5,5).setItem(new Item(new Sprite(1)));
        matrix = new DrawableMatrix(testWorld, testWorld.getSeed(), diameterSize,diameterSize);

        assertNotNull(matrix.matrix[diameterSize][diameterSize].getItem());
        assertNotNull(matrix.matrix[diameterSize+5][diameterSize-5].getItem());
        assertNull(matrix.matrix[diameterSize-5][diameterSize-5].getItem());
    }



}