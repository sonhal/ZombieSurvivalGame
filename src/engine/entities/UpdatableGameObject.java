package engine.entities;

import engine.entities.composites.*;
import engine.entities.interfaces.Hittable;
import engine.entities.interfaces.IUpdatableGameObject;
import engine.entities.world.Tile;
import engine.services.ComponentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class UpdatableGameObject implements IUpdatableGameObject {

    protected List<ScriptableComponent> components;
    private boolean toBeDeleted;
    private boolean isPlayer;

    public UpdatableGameObject(List<ScriptableComponent> components){
        this.components = components;
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
    public Optional<ScriptableComponent> getComponentByType(ComponentType type){
        return ComponentService.getComponentByType(getComponents(), type);
    }


    public void setAsPlayer(boolean bool){
        isPlayer = bool;
    }

    public boolean isPlayer(){
        return isPlayer;
    }

}
