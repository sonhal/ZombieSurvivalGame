package engine.entities.components.componentEventHandlers;

import engine.entities.components.ComponentEvent.ComponentEventEnum;

public class Message {
    public final ComponentEventEnum event;
    public final Object message;

    public Message(ComponentEventEnum event, Object message){
        this.event = event;
        this.message = message;
    }
}
