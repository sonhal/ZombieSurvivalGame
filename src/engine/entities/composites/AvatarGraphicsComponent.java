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
    private double moveAnimationDelay;
    private double lastAnimationStart;

    public AvatarGraphicsComponent(Sprite sprite, double moveAnimationDelay){
        super(ComponentType.GRAPHICS_COMPONENT);
        this.sprites = new ArrayList<>();
        this.sprites.add(sprite);
        this.activeSprite = this.sprites.get(0);
        this.moveAnimationDelay = moveAnimationDelay;
    }

    @Override
    public void update(IGameObject gameObject) {
            if(this.inputMoveDirection != null){
                if(canActivate(moveAnimationDelay, lastAnimationStart)){
                    inputMoveDirection = null;
                }
                else {
                    setActiveSpriteByID(getSpriteMovingIDByDirection(inputMoveDirection));
                }
            }
            else{
                Optional<ScriptableComponent> optionalComponent =
                        getComponentByType(gameObject.getComponents(), ComponentType.TRANSFORM_COMPONENT);
                if(optionalComponent.isPresent()){
                    TransformComponent transformComponent = (TransformComponent)optionalComponent.get();
                    setActiveSpriteByID(getSpriteIDByDirection(transformComponent.getFacingDirection()));
            }
        }
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
        if(message.type == ComponentType.INPUT_COMPONENT){
            inputMoveDirection = (Direction) message.message;
        }

    }

    @Override
    public void innit(IGameObject gameObject) {
        lastAnimationStart = System.currentTimeMillis();
    }
}
