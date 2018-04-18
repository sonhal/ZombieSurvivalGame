package engine.entities;

import engine.entities.composites.*;
import engine.entities.interfaces.IUpdatableGameObject;
import engine.entities.world.Tile;
import engine.services.ComponentService;

import java.util.List;
import java.util.Optional;
import java.util.Queue;

public class ImpUpdatableGameObject extends UpdatableGameObject{

    public ImpUpdatableGameObject(List<ScriptableComponent> components){
        super(components);
        initializeComponents();
    }

    @Override
    public Queue<Sprite> getSprite() {
        Optional<ScriptableComponent> oComponent = getComponentByType(ComponentType.GRAPHICS_COMPONENT);
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
