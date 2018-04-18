package engine.entities;

import engine.entities.composites.*;
import engine.entities.interfaces.IGameObject;
import engine.entities.world.Tile;
import engine.services.ComponentService;

import java.util.List;
import java.util.Optional;
import java.util.Queue;


/**
 * Represents the games base object, GameObjects represents entities in the world.
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
    public Queue<Sprite> getSprite() {
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

    @Override
    public List<ScriptableComponent> getComponents() {
        return components;
    }

    @Override
    public void initializeComponents() {
        components.forEach(scriptableComponent -> scriptableComponent.innit(this));
    }
}
