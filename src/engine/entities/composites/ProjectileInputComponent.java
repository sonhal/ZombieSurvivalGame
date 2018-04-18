package engine.entities.composites;

import engine.controllers.Direction;
import engine.entities.interfaces.IGameObject;

import java.util.ArrayList;
import java.util.List;

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
                    new Message(ComponentEvent.ATTACK_EVENT, flyingDirection));
        }
        else if(canActivate(delay, lastMoveTime)){
            sendMessageToAllComponents(gameObject.getComponents(),
                    new Message(ComponentEvent.MOVE_EVENT, flyingDirection));
            lastMoveTime = System.currentTimeMillis();
        }

    }

    @Override
    public void handle(Message message) {
        if(message.event == ComponentEvent.COLLISION_EVENT){
            if( message.message != null){
                collsionEvent = true;
            }
        }
    }

    @Override
    public void innit(IGameObject gameObject){
        lastMoveTime = System.currentTimeMillis();
        sendMessageToAllComponents(gameObject.getComponents(),
                new Message(ComponentEvent.CHECK_FOR_COLLISION_EVENT, flyingDirection));
    }

    @Override
    public void cleanUp(IGameObject gameObject) {

    }
}
