package engine.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Class responsible for the handling of view events that is sent to the engine
 */
public class EventHandler implements Serializable{
    private ArrayBlockingQueue<ActionEvent> eventQueue;

    public EventHandler(){
        this.eventQueue = new ArrayBlockingQueue<ActionEvent>(50);
    }


    /**
     * Method called when a event is sent to the handler. Adds Event to que
     * @param event ActionEvent
     */
    public void handle(ActionEvent event){
        eventQueue.add(event);
    }

    /**
     * An Object with a reference to the EventHandler can consume ActionEvents from the que.
     * @return ActionEvent, ActionEvent that has been passed from the view.
     */
    public ActionEvent getEvent() {
        if (eventQueue.size() > 0){
            try {
                return eventQueue.take();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
