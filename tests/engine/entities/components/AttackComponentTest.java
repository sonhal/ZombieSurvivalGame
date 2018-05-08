package engine.entities.components;

import engine.controllers.Direction;
import engine.entities.components.ComponentEvent.AttackEvent;
import engine.entities.components.ComponentEvent.ComponentEvent;
import engine.entities.components.ComponentEvent.HitEvent;
import engine.entities.gameobjects.GameObject;
import engine.entities.gameobjects.Sprite;
import engine.world.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AttackComponentTest {
    AttackComponent attackComponent;
    Tile testTile;
    GameObject testObject;
    TestComponent testComponent;


    @BeforeEach
    void setUp() {
        attackComponent = new AttackComponent(10);
        testTile = new Tile(1,1, new Sprite(1));
        testComponent = new TestComponent(ComponentType.HEALTH_COMPONENT) {
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
        ArrayList<ScriptableComponent> list = new ArrayList<>();
        list.add(testComponent);
        list.add(new TransformComponent(testTile));
        testObject = new GameObject(list);

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