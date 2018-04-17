package engine.entities.interfaces;


import engine.entities.composites.*;
import engine.entities.world.Tile;

import java.io.Serializable;
import java.util.List;

public interface IGameObject extends Serializable{

    Sprite getSprite();

    Tile getTile();

    /**
     * Access method that needs to be implemented for the Object to function
     * properly with other Objects accepting a IUpdatableGameObject
     **/
    List<ScriptableComponent> getComponents();

    /**
     * Components the GameObject holds might need to be instantiated after the object has been created.
     * This should be done before the first update to the GameObjects Components is done
     */
    void initializeComponents();





}
