package engine;

import engine.entities.composites.Sprite;
import engine.entities.items.WeaponPickup;
import engine.entities.items.weapons.MeleeWeapon;
import engine.entities.world.World;
import engine.entities.items.Item;
import engine.view.DrawableMatrix;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DrawableMatrixTest {
    DrawableMatrix matrix;
    World testWorld;

    @BeforeEach
    void setUp() {
        testWorld = new World();

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void generateDrawable() {
        int diameterSize = 30;
        testWorld.findTile(0,0).setItem(new WeaponPickup(new Sprite(1), new MeleeWeapon(1,1,1)));
        testWorld.findTile(5,5).setItem(new WeaponPickup(new Sprite(1), new MeleeWeapon(1,1,1)));
        matrix = new DrawableMatrix(testWorld, testWorld.getSeed(), diameterSize,diameterSize);

        assertNotNull(matrix.matrix[diameterSize][diameterSize].getItem());
        assertNotNull(matrix.matrix[diameterSize+5][diameterSize-5].getItem());
        assertNull(matrix.matrix[diameterSize-5][diameterSize-5].getItem());
    }



}