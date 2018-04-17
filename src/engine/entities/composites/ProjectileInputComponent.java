package engine.entities.composites;

import engine.controllers.Direction;
import engine.entities.interfaces.IGameObject;

public class ProjectileInputComponent extends ScriptableComponent{

    private Direction flyingDirection;
    private int delay;
    private double lastMoveTime;

    public ProjectileInputComponent(Direction flyingDirection, int flyingDelay){
        super(ComponentType.INPUT_COMPONENT);
        this.flyingDirection = flyingDirection;
        this.delay = flyingDelay;
    }

    @Override
    public void update(IGameObject gameObject){
        if(canActivate(delay, lastMoveTime)){
            sendMessageToAllComponents(gameObject.getComponents(),
                    new Message(ComponentEvent.MOVE_EVENT, flyingDirection));
            lastMoveTime = System.currentTimeMillis();
        }

    }

    @Override
    public void handle(Message message) {
        //Do nothing
    }

    @Override
    public void innit(IGameObject gameObject){
        lastMoveTime = System.currentTimeMillis();
    }

    @Override
    public void cleanUp(IGameObject gameObject) {

    }
}
