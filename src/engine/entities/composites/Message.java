package engine.entities.composites;

public class Message {
    public final ComponentEvent event;
    public final Object message;

    public Message(ComponentEvent event, Object message){
        this.event = event;
        this.message = message;
    }
}
