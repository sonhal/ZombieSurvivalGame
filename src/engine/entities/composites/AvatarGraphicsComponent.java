package engine.entities.composites;

import engine.controllers.Direction;
import engine.entities.interfaces.IGameObject;


import java.util.ArrayList;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class AvatarGraphicsComponent extends ScriptableComponent implements IGraphicsComponent{
    private ArrayList<Sprite> sprites;
    private Queue<Sprite> activeSprites;
    private Direction inputMoveDirection;
    private Direction currentFacingDirection;
    private double moveAnimationDelay;
    private double lastAnimationStart;
    private boolean isMoving;
    private boolean hitEvent;


    public AvatarGraphicsComponent(Sprite sprite, double moveAnimationDelay){
        super(ComponentType.GRAPHICS_COMPONENT);
        this.sprites = new ArrayList<>();
        this.sprites.add(sprite);
        this.activeSprites = new ArrayBlockingQueue<>(3);
        this.activeSprites.add(this.sprites.get(0));
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
        if(hitEvent){
            if(activeSprites.size() < 2){
                activeSprites.add(new Sprite(17));
            }
            hitEvent = false;
        }
    }

    private boolean newInputReceived(){
        return (inputMoveDirection != null);
    }

    @Override
    public Queue<Sprite> getSprite() {
        return activeSprites;
    }

    @Override
    public ArrayList<Sprite> getSpriteList(){
        return sprites;
    }

    public void addSprite(Sprite newSprite){
        this.sprites.add(newSprite);
    }

    public void setActiveSpriteByID(int id) {
        this.activeSprites.clear();
        this.activeSprites.add(sprites.get(id));
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
        if(message.event == ComponentEvent.DAMAGE_TAKEN_EVENT) {
            hitEvent = true;
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
