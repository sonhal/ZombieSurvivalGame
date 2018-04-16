package engine.entities.composites;

import engine.controllers.Direction;
import engine.entities.interfaces.IUpdatableGameObject;


import java.util.ArrayList;

public class AvatarGraphicsComponent implements Component<IUpdatableGameObject>, IGraphicsComponent{
    private ArrayList<Sprite> sprites;
    private Sprite activeSprite;

    public AvatarGraphicsComponent(Sprite sprite){
        this.sprites = new ArrayList<>();
        this.sprites.add(sprite);
        this.activeSprite = this.sprites.get(0);
    }

    @Override
    public void update(IUpdatableGameObject componentHolder) {
        if(componentHolder.getTransformComponent().getMovingDirection() != null){
            setActiveSpriteByID(getSpriteMovingIDByDirection(
                    componentHolder.getTransformComponent().getMovingDirection()));
        }
        else{
            setActiveSpriteByID(getSpriteIDByDirection(
                    componentHolder.getTransformComponent().getFacingDirection()));
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
}
