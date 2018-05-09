package engine.entities.components;

import engine.controllers.Direction;
import engine.entities.components.ComponentEvent.CollisionEvent;
import engine.entities.components.ComponentEvent.ComponentEvent;
import engine.entities.components.ComponentEvent.DeathEvent;
import engine.entities.components.interfaces.TransformComponent;
import engine.entities.gameobjects.Sprite;
import engine.entities.gameobjects.UpdatableGameObject;
import engine.entities.gameobjects.interfaces.IUpdatableGameObject;
import engine.world.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectileHealthComponentTest {

    ProjectileHealthComponent projectileHealthComponent;
    Tile testTile;
    TransformComponent transformComponent;
    IUpdatableGameObject gameObject;
    TestComponent testComponent;

    @BeforeEach
    void setUp() {
        testTile = new Tile(1,1,new Sprite(1));
        transformComponent = new StaticTransformComponent(testTile);
        projectileHealthComponent = new ProjectileHealthComponent(10);
        testComponent = new TestComponent() {
            boolean deathMessageSent;

            @Override
            public Boolean result() {
                return deathMessageSent;
            }

            @Override
            public void handle(ComponentEvent event){
                if(event instanceof DeathEvent){
                    deathMessageSent = true;
                }
            }
        };

        gameObject = new UpdatableGameObject.Builder(transformComponent)
                .addComponent(projectileHealthComponent)
                .build();
    }

    @Test
    void update() {
    }

    @Test
    void handle() {
        projectileHealthComponent.handle(new CollisionEvent(Direction.UP));
        gameObject.update();
        assertTrue((boolean)testComponent.result());
    }

    @Test
    void innit() {
    }

    @Test
    void cleanUp() {
    }
}