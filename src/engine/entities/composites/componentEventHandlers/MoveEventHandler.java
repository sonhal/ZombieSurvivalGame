package engine.entities.composites.componentEventHandlers;

import engine.entities.composites.ComponentEvent;
import engine.entities.composites.Message;
import engine.entities.composites.ScriptableComponent;

public class MoveEventHandler extends ComponentMessageHandler {


    public MoveEventHandler(ComponentEvent eventTypeToHandle, ScriptableComponent component) {
        super(component);
    }

    @Override
    public void action(Message message, ComponentEvent eventTypeToHandle) {

    }
}
