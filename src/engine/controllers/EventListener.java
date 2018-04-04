package engine.controllers;

/**
 * Describes the interface for objects to be a lister of view events
 */
public interface EventListener {

    void handleEvent(ActionEvent event);
}
