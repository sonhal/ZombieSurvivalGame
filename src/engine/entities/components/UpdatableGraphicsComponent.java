package engine.entities.components;

import engine.controllers.Direction;
import engine.entities.components.ComponentEvent.ComponentEvent;
import engine.entities.components.ComponentEvent.DamageTakenEvent;
import engine.entities.components.ComponentEvent.MoveEvent;
import engine.entities.gameobjects.Sprite;
import engine.entities.components.interfaces.IGraphicsComponent;
import engine.entities.gameobjects.interfaces.IGameObject;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UpdatableGraphicsComponent extends ScriptableComponent implements IGraphicsComponent {
    private List<Sprite> sprites;
    private List<Sprite> activeSprites;
    private Direction inputMoveDirection;
    private Direction currentFacingDirection;
    private double moveAnimationDelay;
    private double lastAnimationStart;
    private boolean isMoving;
    private boolean hitEvent;


    public UpdatableGraphicsComponent(Sprite sprite, double moveAnimationDelay){
        super(ComponentType.GRAPHICS_COMPONENT);
        this.sprites = new ArrayList<>();
        this.sprites.add(sprite);
        this.activeSprites = new ArrayList<>();
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
    public Iterable<Sprite> getActiveSprites() {
        return activeSprites;
    }

    @Override
    public List<Sprite> getSpriteList(){
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
                    spriteId = 1;
                    break;
                case UP:
                    spriteId = 2;
                    break;
                case LEFT:
                    spriteId = 3;
                    break;
                case RIGHT:
                    spriteId = 4;
                    break;
            }
        }
        return spriteId;
    }

    private int getSpriteMovingIDByDirection(Direction direction){
        switch (direction){
            case UP: return  5;
            case DOWN: return 6;
            case LEFT: return 7;
            case RIGHT: return 8;
            default: return 0;
        }
    }

    @Override
    public void handle(ComponentEvent event) {
        if(event instanceof MoveEvent){
            inputMoveDirection = ((MoveEvent)event).getDirection();
        }
        if(event instanceof DamageTakenEvent) {
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
