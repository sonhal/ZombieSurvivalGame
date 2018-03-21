package engine;

import java.util.ArrayList;

public abstract class ScriptableObjectController {

    private ArrayList<ScriptableObject> scriptableObjects;
    private ArrayList<ScriptableObject> scriptableToBeDeleted;

    public ScriptableObjectController(){
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
            scriptableObject.update();
        }
    }
}
