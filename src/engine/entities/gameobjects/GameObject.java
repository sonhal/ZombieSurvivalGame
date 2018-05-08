package engine.entities.gameobjects;

import engine.entities.components.*;
import engine.entities.gameobjects.interfaces.IGameObject;
import engine.world.Tile;
import engine.services.ComponentService;

import java.util.List;
import java.util.Optional;


/**
 * Represents a static GameObject in the game. To be used for GameObjects will not need to put in the game loop.
 *
 * A GameObject and its subtypes behaviour is defined in the components it contains.
 * A GameObject object can be on a Tile object.
 */
public class GameObject implements IGameObject{
    private List<ScriptableComponent> components;

    public GameObject(List<ScriptableComponent> components){
        this.components = components;
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

    @Override
    public Optional<ScriptableComponent> getComponentByType(ComponentType type){
        return ComponentService.getComponentByType(getComponents(), type);
    }

    @Override
    public List<ScriptableComponent> getComponents() {
        return components;
    }

    @Override
    public void initializeComponents() {
        components.forEach(scriptableComponent -> scriptableComponent.innit(this));
    }
}
