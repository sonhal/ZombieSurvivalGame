package engine.entities.gameobjects.interfaces;


import engine.entities.components.*;
import engine.entities.components.interfaces.TransformComponent;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * The Basic GameObject of the game. Represents a interface for all parts
 * of the game to handle a basic object within the game
 */
public interface GameObject extends Serializable{

    /**
     * @return TransformComponent, the transformComponent containing the state of the GameObjects placement in the world
     */
    TransformComponent getTransformComponent();




    /**
     * Access method that needs to be implemented for the Object to function
     * properly with other Objects accepting a IUpdatableGameObject
     **/
    List<ScriptableComponent> getComponents();

    /**
     * Method to get a specific component if it exist in the GameObject
     * @param subClass Subclass of ScriptableComponent
     * @return Optional object that contains the Component if it exists in the GameObject
     */
    <T extends ScriptableComponent>  Optional<ScriptableComponent> getComponentByType(Class<T> subClass);


    /**
     * Components the StaticGameObject holds might need to be instantiated after the object has been created.
     * This should be done before the first update to the GameObjects Components is done
     */
    void initializeComponents();





}
