package engine.entities.components;

import engine.entities.components.ComponentEvent.ComponentEvent;
import engine.entities.gameobjects.interfaces.IGameObject;
import engine.services.ComponentService;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

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
    public ComponentType getType(){
        return type;
    }

    //new method name: delayElapsed?
    /**
     * Method Components can use to evaluate if it can take time dependent actions
     * @param delay, the time between each time the behaviour can happen
     * @param lastUpdateTime, the last time the behaviour happened
     * @return boolean, if the behaviour can happen
     */
    public static boolean canActivate(double delay, double lastUpdateTime){
        return (System.currentTimeMillis() > lastUpdateTime  + delay );
    }

    public static Optional<ScriptableComponent> getComponentByType(List<ScriptableComponent> components, ComponentType type){
        return ComponentService.getComponentByType(components, type);
    }

    public static void sendMessage(List<ScriptableComponent> components, List<ComponentType> recipients, ComponentEvent message){
        ComponentService.getComponentsByListOfTypes(components, recipients)
                .forEach(scriptableComponent -> scriptableComponent.handle(message));
    }

    public void sendMessageToAllComponents(List<ScriptableComponent> components, ComponentEvent message){
        components.forEach(scriptableComponent -> {
            if(scriptableComponent != this){scriptableComponent.handle(message);
            }});
    }

    /**
     *The method called by a gameObject holding a Component each game tick
     * @param gameObject, GameObject that calls the Component
     */
    public abstract void update(IGameObject gameObject);

    /**
     * Messaging method used to send the Component a message, the component implementation defines what type and how it
     * handles calls to this method
     * @param eventWrapper, the Message sent to the Component
     */
    public abstract void handle(ComponentEvent eventWrapper);

    /**
     * Method that initializes the Component if it needs to. A GameObject should attempt to innit all components ith holds
     * immediately after instantiation
     * @param gameObject, the GameObject that calls the Component
     */
    public abstract void innit(IGameObject gameObject);


    /**
     * Method called when a GameObject is to be removed. Gives a ScriptableComponent the oprotunity to do clean-up
     * before the GameObject is removed.
     * @param gameObject, holder of the Component
     */
    public abstract void cleanUp(IGameObject gameObject);

}
