package engine.entities.gameobjects.interfaces;


import engine.entities.components.*;
import engine.world.Tile;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface IGameObject extends Serializable{


    Tile getTile();

    /**
     * Access method that needs to be implemented for the Object to function
     * properly with other Objects accepting a IUpdatableGameObject
     **/
    List<ScriptableComponent> getComponents();

    /**
     * Method to get a specific component if it exist in the IGameObject
     * @param type, ComponentType caller is looking for in the GameObject
     * @return Optional object that contains the Component if it exists in the IGameObject
     */
    Optional<ScriptableComponent> getComponentByType(ComponentType type);


    /**
     * Components the GameObject holds might need to be instantiated after the object has been created.
     * This should be done before the first update to the GameObjects Components is done
     */
    void initializeComponents();





}
