package engine.entities.interfaces;


import engine.entities.composites.*;
import engine.entities.world.Tile;

import java.io.Serializable;
import java.util.ArrayList;

public interface IGameObject extends Serializable{

    Sprite getSprite();

    Tile getTile();

    /**
     * Access method that needs to be implemented for the Object to function
     * properly with other Objects accepting a IUpdatableGameObject
     **/
    ArrayList<ScriptableComponent> getComponents();





}
