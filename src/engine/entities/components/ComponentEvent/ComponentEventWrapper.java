package engine.entities.components.ComponentEvent;

public class ComponentEventWrapper<T extends ComponentEvent> {

    private final T gameEvent;

    public ComponentEventWrapper(final T gameEvent){
        this.gameEvent = gameEvent;
    }

    public T getEvent(){
        return gameEvent;
    }
}
