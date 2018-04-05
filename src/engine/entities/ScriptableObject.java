package engine.entities;

import engine.controllers.ScriptableObjectUpdater;

/**
 * A ScriptableObject is a object that can be updated by a ScriptableObjectUpdater. The implementation defines its own
 * behaviour when the update method is called.
 */
public abstract class ScriptableObject {

    protected ScriptableObjectUpdater controller;
    private boolean toBeDeleted;

    public boolean isDead(){
        return toBeDeleted;
    }
    public void die(){
        toBeDeleted = true;
    }

    public abstract void start();
    public abstract void stop();
    public abstract void update();

}
