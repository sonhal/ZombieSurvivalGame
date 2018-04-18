package engine.entities.composites.componentEventHandlers;

import engine.entities.composites.ComponentEvent;
import engine.entities.composites.Message;
import engine.entities.composites.ScriptableComponent;

public abstract class ComponentMessageHandler {

    private ComponentEvent eventTypeToHandle;
    protected ScriptableComponent component;

    public ComponentMessageHandler(ScriptableComponent component){

        this.component = component;
    }


    public void receiveMessage(Message message){
        if(message.event == eventTypeToHandle){
            action(message, eventTypeToHandle);
        }
    }

    public abstract void action(Message message, ComponentEvent eventTypeToHandle);


}
