package engine.entities.components;

import engine.entities.components.ComponentEvent.ComponentEvent;
import engine.entities.gameobjects.interfaces.GameObject;
import engine.services.ComponentService;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * SuperClass for all Components in the game. Creates a common interface for Components and
 * implements behaviour used by most/all Components
 */
public abstract class ScriptableComponent implements Serializable{

    /**
     * Method Components can use to evaluate if it can take time dependent actions
     * @param delay, the time between each time the behaviour can happen
     * @param lastUpdateTime, the last time the behaviour happened
     * @return boolean, if the behaviour can happen
     */
    public static boolean canActivate(double delay, double lastUpdateTime){
        return (System.currentTimeMillis() > lastUpdateTime  + delay );
    }

    public static Optional<ScriptableComponent> getComponentByType(List<ScriptableComponent> components, Class<ScriptableComponent> subClass){
        return ComponentService.getComponentByType(components, subClass);
    }

    public void sendMessageToAllComponents(List<ScriptableComponent> components, ComponentEvent message){
        components.forEach(scriptableComponent -> {
            if(scriptableComponent != this){scriptableComponent.handle(message);
            }});
    }

    /**
     *The method called by a gameObject holding a Component each game tick
     * @param gameObject, StaticGameObject that calls the Component
     */
    public abstract void update(GameObject gameObject);

    /**
     * Messaging method used to send the Component a message, the component implementation defines what type and how it
     * handles calls to this method
     * @param event, the Message sent to the Component
     */
    public abstract void handle(ComponentEvent event);

    /**
     * Method that initializes the Component if it needs to. A StaticGameObject should attempt to innit all components ith holds
     * immediately after instantiation
     * @param gameObject, the StaticGameObject that calls the Component
     */
    public abstract void innit(GameObject gameObject);


    /**
     * Method called when a StaticGameObject is to be removed. Gives a ScriptableComponent the oprotunity to do clean-up
     * before the StaticGameObject is removed.
     * @param gameObject, holder of the Component
     */
    public abstract void cleanUp(GameObject gameObject);

}
