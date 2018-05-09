package engine.entities.components;

import engine.entities.components.ComponentEvent.ComponentEvent;
import engine.entities.gameobjects.interfaces.GameObject;

public abstract class TestComponent extends ScriptableComponent {


    public abstract Object result();

    @Override
    public void update(GameObject gameObject) {
        //Do nothing
    }

    @Override
    public void handle(ComponentEvent event) {
        //Do nothing
    }

    @Override
    public void innit(GameObject gameObject) {
        //Do nothing
    }

    @Override
    public void cleanUp(GameObject gameObject) {
        //Do nothing
    }
}
