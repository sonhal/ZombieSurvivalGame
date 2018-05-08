package engine.entities.components;

import engine.controllers.Direction;
import engine.entities.components.ComponentEvent.*;
import engine.entities.components.componentEventHandlers.Message;
import engine.entities.gameobjects.interfaces.IGameObject;

public class ProjectileInputComponent extends ScriptableComponent{

    private Direction flyingDirection;
    private int delay;
    private double lastMoveTime;
    private boolean collsionEvent;

    public ProjectileInputComponent(Direction flyingDirection, int flyingDelay){
        super(ComponentType.INPUT_COMPONENT);
        this.flyingDirection = flyingDirection;
        this.delay = flyingDelay;
    }

    @Override
    public void update(IGameObject gameObject){
        if(collsionEvent){
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
                collsionEvent = true;
            }
        }
    }

    @Override
    public void innit(IGameObject gameObject){
        lastMoveTime = System.currentTimeMillis();
        sendMessageToAllComponents(gameObject.getComponents(),
                new CheckForCollisionEvent(flyingDirection));
    }

    @Override
    public void cleanUp(IGameObject gameObject) {

    }
}
