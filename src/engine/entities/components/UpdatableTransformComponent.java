package engine.entities.components;

import engine.controllers.Direction;
import engine.entities.components.ComponentEvent.CollisionEvent;
import engine.entities.components.ComponentEvent.ComponentEvent;
import engine.entities.gameobjects.interfaces.IGameObject;
import engine.world.Tile;


public class UpdatableTransformComponent extends TransformComponent {

    private Direction collision;

    public UpdatableTransformComponent(Tile connectedTile) {
        super(connectedTile);
    }

    public void move(Direction direction, IGameObject gameObject){
            super.move(direction, gameObject);
            setFacingDirection(direction);
    }

    @Override
    public void update (IGameObject gameObject){
            if(move != null){
                setFacingDirection(move);
                if(move != collision){
                    move(move, gameObject);
                    collision = null;
                }
                move = null;
            }
    }

    @Override
    public void handle(ComponentEvent event){
        super.handle(event);
        if(event instanceof CollisionEvent){
            collision = ((CollisionEvent)event).collisonDirection();
        }

    }

}