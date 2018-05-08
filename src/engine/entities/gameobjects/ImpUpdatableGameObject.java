package engine.entities.gameobjects;

import engine.entities.components.*;
import engine.world.Tile;
import engine.services.ComponentService;

import java.util.List;
import java.util.Optional;

public class ImpUpdatableGameObject extends UpdatableGameObject{

    public ImpUpdatableGameObject(List<ScriptableComponent> components){
        super(components);
        initializeComponents();
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
