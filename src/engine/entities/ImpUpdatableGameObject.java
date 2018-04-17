package engine.entities;

import engine.entities.composites.*;
import engine.entities.interfaces.IUpdatableGameObject;
import engine.entities.world.Tile;
import engine.services.ComponentService;

import java.util.List;
import java.util.Optional;

public class ImpUpdatableGameObject extends UpdatableGameObject{

    public ImpUpdatableGameObject(List<ScriptableComponent> components){
        super(components);
        initializeComponents();
    }
    /**
     * Signals to the Handler of the Object that the Object can be deleted.
     *
     * @return boolean, should the Object be deleted.
     */
    @Override
    public boolean isDead() {
        return false;
    }

    /**
     * Method that should result in the IsDead() method returning true.
     * Any Object clean-up should be done here.
     */
    @Override
    public void die() {

    }

    @Override
    public Sprite getSprite() {
        Optional<ScriptableComponent> oComponent = ComponentService.getComponentByType(components,
                ComponentType.GRAPHICS_COMPONENT);
        if(oComponent.isPresent()){
            IGraphicsComponent graphicsComponent = (IGraphicsComponent)oComponent.get();
            return graphicsComponent.getSprite();
        }
        else {
            return null;
        }
    }

    @Override
    public Tile getTile() {
        Optional<ScriptableComponent> oComponent = ComponentService.getComponentByType(components,
                ComponentType.TRANSFORM_COMPONENT);
        if(oComponent.isPresent()){
            TransformComponent transformComponent = (TransformComponent) oComponent.get();
            return transformComponent.getCurrentTile();
        }
        else {
            return null;
        }
    }

}
