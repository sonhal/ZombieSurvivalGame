package engine;

import engine.controllers.Updater;
import engine.entities.components.SingleAttackComponent;
import engine.entities.gameobjects.Sprite;
import engine.entities.items.WeaponPickup;
import engine.entities.items.weapons.Knife;
import engine.entities.items.weapons.Knife;
import engine.view.DrawableTile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DrawableTileTest {

    DrawableTile testTile;
    Updater updater;

    @BeforeEach
    void setUp() {
         testTile = new DrawableTile();
         updater = new Updater();
    }

    @AfterEach
    void tearDown() {

    }
    @Test
    void setGameAndItem() {
        testTile.setGameAndItem(null, new WeaponPickup(new Sprite(1), new Knife(new SingleAttackComponent(1), updater, 4, 5 )));
            assertNull(testTile.getGameObject());
            assertNotNull(testTile.getItem());
    }

    @Test
    void getItem(){
        testTile.setItem(new WeaponPickup(new Sprite(1), new Knife(new SingleAttackComponent(1), updater, 4, 5 )));
        assertNotNull(testTile.getItem());
    }
}