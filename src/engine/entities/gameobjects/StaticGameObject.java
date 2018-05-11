package engine.entities.gameobjects;

import engine.entities.components.*;
import engine.entities.components.interfaces.InputComponent;
import engine.entities.components.interfaces.TransformComponent;
import engine.entities.gameobjects.interfaces.GameObject;
import engine.services.ComponentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Represents a static StaticGameObject in the game. To be used for GameObjects will not need to put in the game loop.
 *
 * A StaticGameObject and its subtypes behaviour is defined in the components it contains.
 * A StaticGameObject object can be on a Tile object.
 */
public class StaticGameObject implements GameObject {
    private List<ScriptableComponent> components;
    protected TransformComponent transformComponent;

    private StaticGameObject(TransformComponent transformComponent, List<ScriptableComponent> components){
        this.components = new ArrayList<>();
        this.transformComponent = transformComponent;
        this.components.addAll(components);
        this.components.add(transformComponent);
        initializeComponents();
    }


    @Override
    public TransformComponent getTransformComponent() {
        return transformComponent;
    }

    @Override
    public <T extends ScriptableComponent>  Optional<ScriptableComponent> getComponentByType(Class<T> subClass){
        return ComponentService.getComponentByType(getComponents(), subClass);
    }

    @Override
    public List<ScriptableComponent> getComponents() {
        return components;
    }

    @Override
    public void initializeComponents() {
        components.forEach(scriptableComponent -> scriptableComponent.innit(this));
    }

    public static class Builder{

        private TransformComponent transformComponent;
        private List<ScriptableComponent> components;

        public Builder(TransformComponent transformComponent){
            this.transformComponent = transformComponent;
            components = new ArrayList<>();
        }

        public Builder addComponent(ScriptableComponent component){
            if(component != null && !(component instanceof TransformComponent )){
                this.components.add(component);
            }
            return this;
        }

        public StaticGameObject build(){
            ArrayList<ScriptableComponent> inputComponents = new ArrayList<>();
            ArrayList<ScriptableComponent> logicComponents = new ArrayList<>();

            components.forEach(scriptableComponent -> {
                if(scriptableComponent instanceof InputComponent){inputComponents.add(scriptableComponent);}
                else {logicComponents.add(scriptableComponent);
                }});
            components.clear();
            components.addAll(inputComponents);
            components.addAll(logicComponents);
            return new StaticGameObject(transformComponent, components);
        }

    }

}
