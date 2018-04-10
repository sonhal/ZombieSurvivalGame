package engine.entities.interfaces;

import engine.controllers.Direction;
import engine.entities.composites.*;
import engine.entities.world.Tile;

public interface IGameObject {

    public Sprite getSprite();

    public Tile getTile();

}
