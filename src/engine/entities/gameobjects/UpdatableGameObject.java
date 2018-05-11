package engine.entities.gameobjects;

import engine.entities.components.*;
import engine.entities.components.interfaces.InputComponent;
import engine.entities.components.interfaces.TransformComponent;
import engine.entities.gameobjects.interfaces.IUpdatableGameObject;
import engine.services.ComponentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;

public class UpdatableGameObject implements IUpdatableGameObject {

    protected List<ScriptableComponent> components;
    protected TransformComponent transformComponent;
    private boolean toBeDeleted;
    private boolean isPlayer;

    private UpdatableGameObject(TransformComponent transformComponent, List<ScriptableComponent> components){
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
    public boolean isDead(){
        if(toBeDeleted){
            getComponents().forEach(scriptableComponent -> scriptableComponent.cleanUp(this));
        }
        return toBeDeleted;
    }

    @Override
    public void die(){
        toBeDeleted = true;
    }


    /**
     * Method called by the Updater Object holding a reference to this object.
     * Should call relevant code the object should run each game tick.
     */
    @Override
    public void update() {
            for (ScriptableComponent component:
                    components) {
                component.update(this);
            }
    }

    @Override
    public void initializeComponents() {
        components.forEach(scriptableComponent -> scriptableComponent.innit(this));
    }


    /**
     * Access method that needs to be implemented for the Object to function
     * properly with other Objects accepting a IUpdatableGameObject
     */
    @Override
    public List<ScriptableComponent> getComponents() {
        return components;
    }

    @Override
    public <T extends ScriptableComponent> Optional<ScriptableComponent> getComponentByType(Class<T> subClass){
        return ComponentService.getComponentByType(getComponents(), subClass);
    }

    @Override
    public void setAsPlayer(boolean bool){
        isPlayer = bool;
    }

    @Override
    public boolean isPlayer(){
        return isPlayer;
    }

    public static class Builder{

        private TransformComponent transformComponent;
        private List<ScriptableComponent> components;

        public Builder(TransformComponent transformComponent){
            this.transformComponent = transformComponent;
            components = new ArrayList<>();
        }

        public UpdatableGameObject.Builder addComponent(ScriptableComponent component){
            if(component != null && !(component instanceof TransformComponent )){
                this.components.add(component);
            }
            return this;
        }

        public UpdatableGameObject build(){
            ArrayList<ScriptableComponent> inputComponents = new ArrayList<>();
            ArrayList<ScriptableComponent> logicComponents = new ArrayList<>();

            components.forEach(scriptableComponent -> {
                if(scriptableComponent instanceof InputComponent){inputComponents.add(scriptableComponent);}
                else {logicComponents.add(scriptableComponent);
                }});
            components.clear();
            components.addAll(inputComponents);
            components.addAll(logicComponents);

            return new UpdatableGameObject(transformComponent, components);
        }

    }
}
