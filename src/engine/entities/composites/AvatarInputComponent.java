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
    private List<ComponentType> moveListenerTypes;
    private List<ComponentType> attackListenerTypes;

    public AvatarInputComponent(EventHandler eventHandler){
        super(ComponentType.INPUT_COMPONENT);
        this.eventHandler = eventHandler;
        moveListenerTypes = new ArrayList<>();
        moveListenerTypes.add(ComponentType.COLLISION_COMPONENT);
        moveListenerTypes.add(ComponentType.GRAPHICS_COMPONENT);
        attackListenerTypes = new ArrayList<>();
        attackListenerTypes.add(ComponentType.WEAPON_COMPONENT);
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
        ComponentService.getComponentsByListOfTypes(gameObject.getComponents(), moveListenerTypes)
                .forEach(scriptableComponent -> scriptableComponent.handle(new Message(ComponentEvent.MOVE_EVENT, direction)));
    }

    private void handleAttacking(IGameObject gameObject, Direction direction){
        ComponentService.getComponentsByListOfTypes(gameObject.getComponents(), moveListenerTypes)
                .forEach(scriptableComponent -> scriptableComponent.handle(new Message(ComponentEvent.MOVE_EVENT, direction)));
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

    }

    @Override
    public void innit(IGameObject gameObject) {

    }
}
