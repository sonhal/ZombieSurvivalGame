package engine.view;

import engine.entities.gameobjects.Sprite;
import engine.entities.items.loot.HealthPotion;
import engine.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DrawableMatrixTest {

    World testWorld;
    DrawableMatrix drawableMatrix;

    @BeforeEach
    void setUp() {
        testWorld = new World();
        testWorld.createNewGameWorld(40);
        testWorld.getSeed().getLeft().getLeft().getLeft().getLeft().getLeft().setItem(new HealthPotion(new Sprite(1),"Health", 100));
        testWorld.getSeed().setItem(new HealthPotion(new Sprite(1),"Health", 100));
        drawableMatrix = new DrawableMatrix(testWorld, testWorld.getSeed(),5,5);
    }


    @Test
    void generateDrawable() {
        DrawableTile[][] drawableTiles = drawableMatrix.generateDrawable(testWorld, testWorld.getSeed(),5,5);
        assertNotNull(drawableTiles[5][0].getItem());
    }
}