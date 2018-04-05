package engine.controllers;

import engine.entities.ScriptableObject;

import java.util.ArrayList;

/**
 * Class defining and implementing features a controller of ScriptableObjects needs
 */
public abstract class ScriptableObjectUpdater {

    private ArrayList<ScriptableObject> scriptableObjects;
    private ArrayList<ScriptableObject> scriptableToBeDeleted;

    public ScriptableObjectUpdater(){
        this.scriptableObjects = new ArrayList<>();
        this.scriptableToBeDeleted = new ArrayList<>();
    }

    public void addToUpdateList(ScriptableObject scriptableObject){
        this.scriptableObjects.add(scriptableObject);
    }

    public void addToBeDeletedList(ScriptableObject scriptableObject){
        scriptableToBeDeleted.add(scriptableObject);
    }

    public ArrayList<ScriptableObject> getScriptableObjects(){
        return scriptableObjects;
    }

    public void updateScriptableObjects(){
        getScriptableObjects().removeAll(scriptableToBeDeleted);
        scriptableObjects.trimToSize();

        for (ScriptableObject scriptableObject:
                scriptableObjects) {
            if (scriptableObject.isDead()){addToBeDeletedList(scriptableObject);}
            else scriptableObject.update();
        }
    }
}
