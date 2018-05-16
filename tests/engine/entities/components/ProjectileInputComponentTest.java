package engine.entities.components;

import engine.controllers.Direction;
import engine.entities.components.ComponentEvent.AttackEvent;
import engine.entities.components.ComponentEvent.ComponentEvent;
import engine.entities.components.ComponentEvent.MoveEvent;
import engine.entities.components.interfaces.TransformComponent;
import engine.entities.gameobjects.UpdatableGameObject;
import engine.entities.gameobjects.interfaces.GameObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProjectileInputComponentTest {

    UpdatableGameObject gameObject;
    ProjectileInputComponent projectileInputComponent;
    TestComponent testMoveComponent;
    TestComponent testAttackComponent;
    GameObject testObject;
    ArrayList<ScriptableComponent> testList;



    @BeforeEach
    void setUp() {

        testMoveComponent = new TestComponent() {
            Direction move;

            @Override
            public Direction result() {
                return move;
            }

            @Override
            public void handle(ComponentEvent event) {
                if(event instanceof MoveEvent){
                    move =  ((MoveEvent) event).getDirection();
                }
            }
        };
        testAttackComponent = new TestComponent() {
            Direction attack;

            @Override
            public Direction result() {
                return attack;
            }

            @Override
            public void handle(ComponentEvent event) {
                if(event instanceof AttackEvent){
                    attack  =  ((AttackEvent) event).getAttackDirection();
                }
            }
        };

        testList = new ArrayList<>();
        testList.add(testAttackComponent);
        testList.add(testMoveComponent);
        testObject = new GameObject() {
            @Override
            public TransformComponent getTransformComponent() {
                return null;
            }

            @Override
            public List<ScriptableComponent> getComponents() {
                return testList;
            }

            @Override
            public <T extends ScriptableComponent> Optional<ScriptableComponent> getComponentByType(Class<T> subClass) {
                return Optional.empty();
            }

            @Override
            public void initializeComponents() {

            }
        };

        projectileInputComponent = new ProjectileInputComponent(Direction.UP, 100);

    }

    @Test
    void update() {
        projectileInputComponent.update(gameObject);
        System.out.print(testMoveComponent.result());
    }

    @Test
    void handle() {
    }

    @Test
    void innit() {
    }

    @Test
    void cleanUp() {
    }
}