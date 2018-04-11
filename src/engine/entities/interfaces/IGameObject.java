package engine.entities.interfaces;

import engine.controllers.Direction;
import engine.entities.composites.*;
import engine.entities.world.Tile;

import java.io.Serializable;

public interface IGameObject extends Serializable{

    public Sprite getSprite();

    public Tile getTile();

}
