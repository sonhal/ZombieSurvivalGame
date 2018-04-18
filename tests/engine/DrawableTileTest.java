package engine;

import engine.entities.Sprite;
import engine.entities.items.WeaponPickup;
import engine.entities.items.weapons.MeleeWeapon;
import engine.view.DrawableTile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DrawableTileTest {

    DrawableTile testTile;

    @BeforeEach
    void setUp() {
         testTile = new DrawableTile();
    }

    @AfterEach
    void tearDown() {

    }
    @Test
    void setGameAndItem() {
        testTile.setGameAndItem(null, new WeaponPickup(new Sprite(1), new MeleeWeapon(1,1,1)));
            assertNull(testTile.getGameObject());
            assertNotNull(testTile.getItem());
    }

    @Test
    void getItem(){
        testTile.setItem(new WeaponPickup(new Sprite(1), new MeleeWeapon(1,1,1)));
        assertNotNull(testTile.getItem());
    }
}