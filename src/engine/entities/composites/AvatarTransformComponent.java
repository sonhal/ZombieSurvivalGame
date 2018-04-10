package engine.entities.composites;

import engine.controllers.Direction;
import engine.entities.Avatar;


public class AvatarTransformComponent extends TransformComponent {
    private Direction movingDirection;
    private double lastMoveTime;
    private double moveDelay;
    private double movingTime;
    private double movingTimeCounter;
    public AvatarTransformComponent(double moveDelay, double movingTime) {
        this.moveDelay = moveDelay;
        this.movingTime = movingTime;
    }

    public void move(Avatar avatar, Direction direction){
        if(canMove()){
            if(avatar.getCollisionComponent().collided() == null){
                avatar.getCollisionComponent().clearCollided();
                super.move(direction);
            }
            avatar.getTransformComponent().setFacingDirection(direction);

            this.lastMoveTime = System.currentTimeMillis();
            if(movingDirection == null) {
                movingTimeCounter = System.currentTimeMillis();
            }
            this.movingDirection = direction;
        }

    }

    public void update(Avatar avatar){
        if(this.movingDirection != null){
            if(TimeComponent.canUpdate(movingTime, movingTimeCounter)){
                movingDirection = null;
            }
        }
        if(avatar.getInputComponent().getMoveEvent() != null){
            move(avatar, avatar.getInputComponent().getMoveEvent());
        }
    }

    private boolean canMove()
    {
        return TimeComponent.canUpdate(moveDelay, lastMoveTime);
    }

    public Direction getMovingDirection() {
        return movingDirection;
    }

    public void setMoveDelay(double moveDelay) {
        if (moveDelay > 0){
            this.moveDelay = moveDelay;
        }
        else {System.out.println("Cant set move delay less than 0");}
    }

    public void setMovingTime(double movingTime) {
        this.movingTime = movingTime;
    }
}