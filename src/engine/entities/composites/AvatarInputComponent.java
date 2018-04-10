package engine.entities.composites;

import engine.controllers.ActionEvent;
import engine.controllers.Direction;
import engine.controllers.EventHandler;
import engine.entities.Avatar;

public class AvatarInputComponent implements InputComponent {

    private WeaponComponent weaponComponent;
    private TransformComponent transformComponent;
    private EventHandler eventHandler;
    private Direction moveEvent;
    private Direction attackEvent;

    public AvatarInputComponent(EventHandler eventHandler, TransformComponent tc, WeaponComponent wc){
        transformComponent = tc;
        weaponComponent = wc;
        this.eventHandler = eventHandler;
    }


    public AvatarInputComponent(TransformComponent tc){
        transformComponent = tc;
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    public void update(Avatar avatar){
        moveEvent = null;
        attackEvent = null;
        ActionEvent event = eventHandler.getEvent();
        if(event != null){
            handleEvent(event);
        }
    }

    private void handleMoving(Direction direction){
        /*
        if(transformComponent != null){
         transformComponent.move(direction);
        }
         */
           moveEvent = direction;
    }

    private void handleAttacking(Direction direction){
        /*
        if(weaponComponent != null && transformComponent != null){
            weaponComponent.attack(direction, transformComponent);
        }
         */
        attackEvent = direction;
    }


    public void handleEvent(ActionEvent event) {
        switch (event){
            case MOVE_UP: handleMoving(Direction.LEFT);
                break;
            case MOVE_DOWN: handleMoving(Direction.RIGHT);
                break;
            case MOVE_LEFT: handleMoving(Direction.UP);
                break;
            case MOVE_RIGHT: handleMoving(Direction.DOWN);
                break;
            case ATTACK_UP: handleAttacking(Direction.UP);
                break;
            case ATTACK_DOWN: handleAttacking(Direction.DOWN);
                break;
            case ATTACK_LEFT: handleAttacking(Direction.LEFT);
                break;
            case ATTACK_RIGHT: handleAttacking(Direction.RIGHT);
                break;
        }
    }

    public Direction getMoveEvent(){
        return moveEvent;
    }

    public Direction getAttackEvent() {
        return attackEvent;
    }
}
