package engine.entities.components.interfaces;

import engine.controllers.Direction;
import engine.entities.components.ScriptableComponent;
import engine.entities.gameobjects.interfaces.GameObject;
import engine.world.Tile;

public abstract class TransformComponent extends ScriptableComponent {

    public abstract Tile getCurrentTile();

    public abstract void setCurrentTile(Tile tile);

    public abstract Direction getFacingDirection();

    public abstract void setFacingDirection(Direction facingDirection);

    public abstract void move(Direction direction, GameObject gameObject);
}
