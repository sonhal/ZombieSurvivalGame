package engine.entities.composites;

import engine.controllers.Direction;
import engine.entities.Avatar;
import engine.entities.interfaces.IGameObject;


public class AvatarTransformComponent extends TransformComponent {
    private double lastMoveTime;
    private double moveDelay;

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
            move(move);
        }
    }

    public void setMoveDelay(double moveDelay) {
        if (moveDelay > 0){
            this.moveDelay = moveDelay;
        }
        else {System.out.println("Cant set move delay less than 0");}
    }

}