package engine.controllers;

import java.util.ArrayList;

/**
 * Class responsible for the handling of view events that is sent to the engine
 */
public class EventHandler {
    private ArrayList<EventListener> listeners;

    public EventHandler(){
        this.listeners = new ArrayList<>();
    }


    /**
     * Method called when a event is sent to the handler. Sends the event to all listeners
     * @param event ActionEvent
     */
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
