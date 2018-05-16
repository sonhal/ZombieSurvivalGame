package engine.entities.components;

import engine.controllers.Direction;
import engine.entities.components.ComponentEvent.*;
import engine.entities.components.interfaces.InputComponent;
import engine.entities.gameobjects.interfaces.GameObject;

/**
 * ProjectileInputComponent directs a GameObject in one direction.
 * Tries to attack a GameObject if it Collides.
 */
public class ProjectileInputComponent extends InputComponent {

    protected Direction flyingDirection;
    protected int delay;
    protected double lastMoveTime;
    protected boolean collisionEvent;

    public ProjectileInputComponent(Direction flyingDirection, int flyingDelay){
        this.flyingDirection = flyingDirection;
        this.delay = flyingDelay;
    }

    @Override
    public void update(GameObject gameObject){
        if(collisionEvent){
            sendMessageToAllComponents(gameObject.getComponents(),
                    new AttackEvent(flyingDirection));
        }
        else if(canActivate(delay, lastMoveTime)){
            sendMessageToAllComponents(gameObject.getComponents(),
                    new MoveEvent(flyingDirection));
            lastMoveTime = System.currentTimeMillis();
        }

    }

    @Override
    public void handle(ComponentEvent event) {
        if(event instanceof CollisionEvent){
            if(((CollisionEvent)event).collisonDirection() != null){
                collisionEvent = true;
            }
        }
    }

    @Override
    public void innit(GameObject gameObject){
        lastMoveTime = System.currentTimeMillis();
        sendMessageToAllComponents(gameObject.getComponents(),
                new CheckForCollisionEvent(flyingDirection));
    }

    @Override
    public void cleanUp(GameObject gameObject) {

    }
}
