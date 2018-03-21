package engine;

import java.util.ArrayList;

public class EventHandler {
    private ArrayList<EventListener> listeners;

    public EventHandler(){
        this.listeners = new ArrayList<>();
    }


    public void handle(ActionEvent event){

        for (EventListener listener:
             listeners) {
            listener.handleEvent(event);
        }
    }

    public void setNewListener(EventListener listener){
        this.listeners.add(listener);
    }
    public void removeListener(EventListener listener){
        this.listeners.remove(listener);
    }
}
