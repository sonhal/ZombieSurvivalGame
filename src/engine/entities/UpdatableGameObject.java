package engine.entities;

import engine.entities.composites.*;
import engine.entities.interfaces.IUpdatableGameObject;
import engine.entities.world.Tile;

public abstract class UpdatableGameObject implements IUpdatableGameObject {

    private AvatarTransformComponent transformComponent;
    private AvatarGraphicsComponent graphicsComponent;

    public UpdatableGameObject(AvatarGraphicsComponent gc){
        this.graphicsComponent = gc;
    }

    public UpdatableGameObject(Sprite sprite){
        this.graphicsComponent = new AvatarGraphicsComponent(sprite);
    }

    private boolean toBeDeleted;

    public boolean isDead(){
        return toBeDeleted;
    }

    public void die(){
        toBeDeleted = true;
    }

    @Override
    public Tile getTile(){
        return this.transformComponent.getCurrentTile();
    }

    @Override
    public Sprite getSprite(){
        return graphicsComponent.getSprite();
    }

    public abstract void update();

    public AvatarGraphicsComponent getGraphicsComponent(){return graphicsComponent;}

    public AvatarTransformComponent getTransformComponent(){return transformComponent;}

    public void setTransformComponent(AvatarTransformComponent avatarTransformComponent){
        this.transformComponent = avatarTransformComponent;
    }

    public void setGraphicsComponent(AvatarGraphicsComponent graphicsComponent) {
        this.graphicsComponent = graphicsComponent;
    }
}
