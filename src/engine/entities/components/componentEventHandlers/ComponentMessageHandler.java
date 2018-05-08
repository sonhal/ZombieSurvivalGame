package engine.entities.components.componentEventHandlers;

import engine.entities.components.ComponentEvent.ComponentEventEnum;
import engine.entities.components.ScriptableComponent;

public abstract class ComponentMessageHandler {

    private ComponentEventEnum eventTypeToHandle;
    protected ScriptableComponent component;

    public ComponentMessageHandler(ScriptableComponent component){

        this.component = component;
    }


    public void receiveMessage(Message message){
        if(message.event == eventTypeToHandle){
            action(message, eventTypeToHandle);
        }
    }

    public abstract void action(Message message, ComponentEventEnum eventTypeToHandle);


}
