package engine.entities.composites;

import engine.controllers.Direction;
import engine.entities.interfaces.IGameObject;
import engine.entities.world.Tile;


public class AvatarTransformComponent extends TransformComponent {

    private Direction collision;

    public AvatarTransformComponent(Tile connectedTile) {
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
    public void handle(Message message){
        super.handle(message);
        if(message.event == ComponentEvent.COLLISION_EVENT){
            collision = (Direction)message.message;
        }

    }

}