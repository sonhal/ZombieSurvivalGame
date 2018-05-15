package engine.entities.components;

import engine.controllers.Direction;
import engine.entities.components.ComponentEvent.CollisionEvent;
import engine.entities.components.ComponentEvent.ComponentEvent;
import engine.entities.gameobjects.interfaces.GameObject;
import engine.world.Tile;


public class UpdatableTransformComponent extends StaticTransformComponent {

    protected Direction collision;

    public UpdatableTransformComponent(Tile connectedTile) {
        super(connectedTile);
    }

    public void move(Direction direction, GameObject gameObject){
            super.move(direction, gameObject);
            setFacingDirection(direction);
    }

    @Override
    public void update (GameObject gameObject){
            if(move != null){
                setFacingDirection(move);
                if(move != collision){
                    move(move, gameObject);
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