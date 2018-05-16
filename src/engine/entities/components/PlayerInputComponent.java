package engine.entities.components;

import engine.controllers.ActionEvent;
import engine.controllers.Direction;
import engine.controllers.EventHandler;
import engine.entities.components.ComponentEvent.*;
import engine.entities.components.interfaces.InputComponent;
import engine.entities.gameobjects.interfaces.GameObject;

/**
 * Component to only be used by GameObjects that should be controlled by Player key presses.
 * Listen for new events from the EventHandler and relays them through the GameObject.
 */
public class PlayerInputComponent extends InputComponent {

    private EventHandler eventHandler;
    private double moveDelay;
    private double lastMoveEvent;

    public PlayerInputComponent(EventHandler eventHandler, double moveDelay){
        this.eventHandler = eventHandler;
        this.moveDelay = moveDelay;
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    public void update(GameObject gameObject){
        ActionEvent event = eventHandler.getEvent();
        if(event != null){
            handleEvent(gameObject, event);
        }
    }

    private void handleMoving(GameObject gameObject, Direction direction){
        if(canActivate(moveDelay, lastMoveEvent)){
            sendMessageToAllComponents(gameObject.getComponents(), new MoveEvent(direction));
            lastMoveEvent = System.currentTimeMillis();
        }

    }

    private void handleAttacking(GameObject gameObject, Direction direction){
        sendMessageToAllComponents(gameObject.getComponents(), new AttackEvent(direction));
    }

    private void handleChangeWeapon(GameObject gameObject){
        sendMessageToAllComponents(gameObject.getComponents(), new ChangeWeaponEvent());
    }


    private void handleEvent(GameObject gameObject, ActionEvent event) {
        switch (event){
            case MOVE_UP: handleMoving(gameObject, Direction.UP);
                break;
            case MOVE_DOWN: handleMoving(gameObject, Direction.DOWN);
                break;
            case MOVE_LEFT: handleMoving(gameObject, Direction.LEFT);
                break;
            case MOVE_RIGHT: handleMoving(gameObject, Direction.RIGHT);
                break;
            case ATTACK_UP: handleAttacking(gameObject, Direction.UP);
                break;
            case ATTACK_DOWN: handleAttacking(gameObject, Direction.DOWN);
                break;
            case ATTACK_LEFT: handleAttacking(gameObject, Direction.LEFT);
                break;
            case ATTACK_RIGHT: handleAttacking(gameObject, Direction.RIGHT);
                break;
            case CHANGE_WEAPON: handleChangeWeapon(gameObject);
                break;
        }
    }

    @Override
    public void handle(ComponentEvent message) {
        //Do nothing
    }

    @Override
    public void innit(GameObject gameObject) {
        //Do nothing
    }

    @Override
    public void cleanUp(GameObject gameObject) {

    }
}
