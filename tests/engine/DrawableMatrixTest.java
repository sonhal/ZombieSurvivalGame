package engine;


import engine.controllers.Updater;
import engine.entities.components.SingleAttackComponent;
import engine.entities.components.SoundEffectComponent;
import engine.entities.gameobjects.Sprite;
import engine.entities.items.WeaponPickup;
import engine.entities.items.weapons.Knife;
import engine.entities.items.weapons.WeaponType;
import engine.services.audio.Sound;
import engine.world.World;
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
        testWorld.createNewGameWorld(50);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void generateDrawable() {
        Updater updater = new Updater();
        int diameterSize = 50;
        testWorld.findTile(0,0).setItem(new WeaponPickup(new Sprite(1), new Knife(WeaponType.BASIC_KNIFE, new SoundEffectComponent(100, Sound.HIT_1),new SingleAttackComponent(1), updater, 4)));
        testWorld.findTile(5,5).setItem(new WeaponPickup(new Sprite(1), new Knife(WeaponType.BASIC_KNIFE, new SoundEffectComponent(100, Sound.HIT_1),new SingleAttackComponent(1), updater, 4)));
        matrix = new DrawableMatrix(testWorld, testWorld.getSeed(), diameterSize,diameterSize);

        assertNotNull(matrix.matrix[diameterSize][diameterSize].getItem());
        assertNotNull(matrix.matrix[diameterSize+5][diameterSize+5].getItem());
        assertNull(matrix.matrix[diameterSize-5][diameterSize-5].getItem());
    }



}