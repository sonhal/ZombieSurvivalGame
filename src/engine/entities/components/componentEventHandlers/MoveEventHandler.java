package engine.entities.components.componentEventHandlers;

import engine.entities.components.ComponentEvent.ComponentEventEnum;
import engine.entities.components.ScriptableComponent;

public class MoveEventHandler extends ComponentMessageHandler {


    public MoveEventHandler(ComponentEventEnum eventTypeToHandle, ScriptableComponent component) {
        super(component);
    }

    @Override
    public void action(Message message, ComponentEventEnum eventTypeToHandle) {

    }
}
