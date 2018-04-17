package engine.entities.composites;

import engine.controllers.Direction;
import engine.entities.interfaces.IGameObject;


public class AvatarTransformComponent extends TransformComponent {
    private double lastMoveTime;
    private double moveDelay;
    private Direction collision;

    public AvatarTransformComponent(double moveDelay) {
        this.moveDelay = moveDelay;
    }

    public void move(Direction direction){
        if(canActivate(moveDelay, lastMoveTime)){
            super.move(direction);
            setFacingDirection(direction);
            this.lastMoveTime = System.currentTimeMillis();
        }
    }

    @Override
    public void update (IGameObject gameObject){
        if(move != null){
            setFacingDirection(move);
            if(move != collision){
                move(move);
                collision = null;
            }
            move = null;
        }
    }

    public void setMoveDelay(double moveDelay) {
        if (moveDelay > 0){
            this.moveDelay = moveDelay;
        }
        else {System.out.println("Cant set move delay less than 0");}
    }

    @Override
    public void handle(Message message){
        super.handle(message);
        if(message.event == ComponentEvent.COLLISION_EVENT){
            collision = (Direction)message.message;
        }

    }

}