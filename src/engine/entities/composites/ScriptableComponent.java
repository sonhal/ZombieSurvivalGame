package engine.entities.composites;

import engine.entities.interfaces.IGameObject;

import java.io.Serializable;

/**
 * SuperClass for all Components in the game. Creates a common interface for Components and
 * implements behaviour used by most/all Components
 */
public abstract class ScriptableComponent implements Serializable{


    private ComponentType type;

    ScriptableComponent(ComponentType type){
        this.type = type;
    }

    /**
     * Every Component has a type that describes the responsibility and interests of the Component
     * @return ComponentType, Enum giving a broad definition about domain the component handles
     */
    ComponentType getType(){
        return type;
    }

    /**
     * Method Components can use to evaluate if it can take time dependent actions
     * @param delay, the time between each time the behaviour can happen
     * @param lastUpdateTime, the last time the behaviour happened
     * @return boolean, if the behaviour can happen
     */
    public static boolean canActivate(double delay, double lastUpdateTime){
        return (System.currentTimeMillis() > lastUpdateTime  + delay );
    }

    /**
     *The method called by a gameObject holding a Component each game tick
     * @param gameObject, GameObject that calls the Component
     */
    abstract void update(IGameObject gameObject);

    /**
     * Messaging method used to send the Component a message, the component implementation defines what type and how it
     * handles calls to this method
     * @param message, the message sent to the Component
     */
    abstract void handle(Object message);

}
