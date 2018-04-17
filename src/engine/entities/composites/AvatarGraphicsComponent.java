package engine.entities.composites;

import engine.controllers.Direction;
import engine.entities.interfaces.IGameObject;
import engine.entities.interfaces.IUpdatableGameObject;


import java.util.ArrayList;
import java.util.Optional;

public class AvatarGraphicsComponent extends ScriptableComponent implements IGraphicsComponent{
    private ArrayList<Sprite> sprites;
    private Sprite activeSprite;
    private Direction inputMoveDirection;
    private Direction currentFacingDirection;
    private double moveAnimationDelay;
    private double lastAnimationStart;
    private boolean isMoving;


    public AvatarGraphicsComponent(Sprite sprite, double moveAnimationDelay){
        super(ComponentType.GRAPHICS_COMPONENT);
        this.sprites = new ArrayList<>();
        this.sprites.add(sprite);
        this.activeSprite = this.sprites.get(0);
        this.moveAnimationDelay = moveAnimationDelay;
    }

    @Override
    public void update(IGameObject gameObject) {
        if(inputMoveDirection != null){
            if(isMoving && currentFacingDirection == inputMoveDirection){
                if(canActivate(moveAnimationDelay, lastAnimationStart)){
                    setActiveSpriteByID(getSpriteIDByDirection(currentFacingDirection));
                    lastAnimationStart = System.currentTimeMillis();
                }
            }
            else {
                currentFacingDirection = inputMoveDirection;
                isMoving = true;
                setActiveSpriteByID(getSpriteMovingIDByDirection(inputMoveDirection));
                lastAnimationStart = System.currentTimeMillis();
            }
            inputMoveDirection = null;
        }
        else {
            if(isMoving){
                if(canActivate(moveAnimationDelay, lastAnimationStart)){
                    setActiveSpriteByID(getSpriteIDByDirection(currentFacingDirection));
                    isMoving = false;
                    lastAnimationStart = System.currentTimeMillis();
                }
            }
        }

        /*
        if(newInputReceived()){
            if(currentFacingDirection == inputMoveDirection){
                if(canActivate(moveAnimationDelay, lastAnimationStart)) {
                    setActiveSpriteByID(getSpriteIDByDirection(currentFacingDirection));
                    lastAnimationStart = System.currentTimeMillis();
                }
                else{
                    currentFacingDirection = inputMoveDirection;
                    setActiveSpriteByID(getSpriteMovingIDByDirection(inputMoveDirection));
                }
                    }
            else{
                currentFacingDirection = inputMoveDirection;
                setActiveSpriteByID(getSpriteMovingIDByDirection(inputMoveDirection));
                lastAnimationStart = System.currentTimeMillis();
            }
            inputMoveDirection = null;
        }
        else {
            if(canActivate(moveAnimationDelay, lastAnimationStart)) {
                setActiveSpriteByID(getSpriteIDByDirection(currentFacingDirection));
                lastAnimationStart = System.currentTimeMillis();
            }
        }*/
    }

    private boolean newInputReceived(){
        return (inputMoveDirection != null);
    }

    public Sprite getSprite() {
        return activeSprite;
    }

    public ArrayList<Sprite> getSpriteList(){
        return sprites;
    }

    public void addSprite(Sprite newSprite){
        this.sprites.add(newSprite);
    }

    public void setActiveSpriteByID(int id) {
        this.activeSprite = sprites.get(id);
    }

    private int getSpriteIDByDirection(Direction direction){
        int spriteId = 0;
        if(direction != null){
            switch (direction){
                case DOWN:
                    spriteId = 4;
                    break;
                case UP:
                    spriteId = 3;
                    break;
                case LEFT:
                    spriteId = 2;
                    break;
                case RIGHT:
                    spriteId = 1;
                    break;
            }
        }
        return spriteId;
    }

    private int getSpriteMovingIDByDirection(Direction direction){
        switch (direction){
            case UP: return  7;
            case DOWN: return 8;
            case LEFT: return 5;
            case RIGHT: return 6;
            default: return 0;
        }
    }

    @Override
    public void handle(Message message) {
        if(message.event == ComponentEvent.MOVE_EVENT){
            inputMoveDirection = (Direction) message.message;
        }

    }

    @Override
    public void innit(IGameObject gameObject) {
        lastAnimationStart = System.currentTimeMillis();
        Optional<ScriptableComponent> optionalComponent =
                getComponentByType(gameObject.getComponents(), ComponentType.TRANSFORM_COMPONENT);
        if (optionalComponent.isPresent()) {
            TransformComponent transformComponent = (TransformComponent) optionalComponent.get();
            setActiveSpriteByID(getSpriteIDByDirection(transformComponent.getFacingDirection()));
            inputMoveDirection = transformComponent.getFacingDirection();
        }
    }

    @Override
    public void cleanUp(IGameObject gameObject) {

    }
}
