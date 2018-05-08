package engine.entities.components;

import engine.entities.components.ComponentEvent.ComponentEvent;
import engine.entities.gameobjects.interfaces.IGameObject;

public abstract class TestComponent extends ScriptableComponent {

    TestComponent(ComponentType type) {
        super(type);
    }

    public abstract Object result();

    @Override
    public void update(IGameObject gameObject) {
        //Do nothing
    }

    @Override
    public void handle(ComponentEvent event) {
        //Do nothing
    }

    @Override
    public void innit(IGameObject gameObject) {
        //Do nothing
    }

    @Override
    public void cleanUp(IGameObject gameObject) {
        //Do nothing
    }
}
