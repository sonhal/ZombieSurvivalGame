package engine.entities.components;

import engine.controllers.Direction;
import engine.entities.components.ComponentEvent.AttackEvent;
import engine.entities.components.ComponentEvent.ComponentEvent;
import engine.entities.components.ComponentEvent.HitEvent;
import engine.entities.gameobjects.StaticGameObject;
import engine.entities.gameobjects.Sprite;
import engine.world.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AttackComponentTest {
    SingleAttackComponent attackComponent;
    Tile testTile;
    StaticGameObject testObject;
    TestComponent testComponent;


    @BeforeEach
    void setUp() {
        attackComponent = new SingleAttackComponent(10);
        testTile = new Tile(1,1, new Sprite(1));
        testComponent = new TestComponent() {
            boolean wasHit;

            @Override
            public Boolean result() {
                return wasHit;
            }

            @Override
            public void handle(ComponentEvent event){
                if(event instanceof HitEvent){
                    wasHit = true;
                }
            }
        };
        testObject = new StaticGameObject.Builder(new StaticTransformComponent(testTile))
                .addComponent(testComponent)
                .build();

    }

    @Test
    void tryAttack() {
        attackComponent.tryAttack(testTile);
        assertTrue((Boolean) testComponent.result(), "Result should be true as the test object was hit");
    }

    @Test
    void update() {
    }

    @Test
    void handle() {
        attackComponent.handle(new AttackEvent(Direction.UP));
    }

    @Test
    void innit() {
    }

    @Test
    void cleanUp() {
    }
}