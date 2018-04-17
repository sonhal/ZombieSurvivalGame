package engine.entities.composites;

import engine.controllers.ActionEvent;
import engine.controllers.Direction;
import engine.controllers.EventHandler;
import engine.entities.interfaces.IGameObject;
import engine.services.ComponentService;

import java.util.ArrayList;
import java.util.List;

public class AvatarInputComponent extends ScriptableComponent{

    private EventHandler eventHandler;
    private double moveDelay;
    private double lastMoveEvent;

    public AvatarInputComponent(EventHandler eventHandler, double moveDelay){
        super(ComponentType.INPUT_COMPONENT);
        this.eventHandler = eventHandler;
        this.moveDelay = moveDelay;
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    public void update(IGameObject gameObject){
        ActionEvent event = eventHandler.getEvent();
        if(event != null){
            handleEvent(gameObject, event);
        }
    }

    private void handleMoving(IGameObject gameObject, Direction direction){
        if(canActivate(moveDelay, lastMoveEvent)){
            sendMessageToAllComponents(gameObject.getComponents(), new Message(ComponentEvent.MOVE_EVENT, direction));
            lastMoveEvent = System.currentTimeMillis();
        }

    }

    private void handleAttacking(IGameObject gameObject, Direction direction){
        sendMessageToAllComponents(gameObject.getComponents(), new Message(ComponentEvent.ATTACK_EVENT, direction));
    }


    private void handleEvent(IGameObject gameObject, ActionEvent event) {
        switch (event){
            case MOVE_UP: handleMoving(gameObject, Direction.LEFT);
                break;
            case MOVE_DOWN: handleMoving(gameObject, Direction.RIGHT);
                break;
            case MOVE_LEFT: handleMoving(gameObject, Direction.UP);
                break;
            case MOVE_RIGHT: handleMoving(gameObject, Direction.DOWN);
                break;
            case ATTACK_UP: handleAttacking(gameObject, Direction.UP);
                break;
            case ATTACK_DOWN: handleAttacking(gameObject, Direction.DOWN);
                break;
            case ATTACK_LEFT: handleAttacking(gameObject, Direction.LEFT);
                break;
            case ATTACK_RIGHT: handleAttacking(gameObject, Direction.RIGHT);
                break;
        }
    }

    @Override
    public void handle(Message message) {
        //Do nothing
    }

    @Override
    public void innit(IGameObject gameObject) {
        //Do nothing
    }

    @Override
    public void cleanUp(IGameObject gameObject) {

    }
}
