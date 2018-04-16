package engine.entities.composites;

public class Message {
    public final ComponentType type;
    public final Object message;

    public Message(ComponentType type, Object message){
        this.type = type;
        this.message = message;
    }
}
