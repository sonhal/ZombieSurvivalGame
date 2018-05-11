package engine.controllers;

import engine.entities.gameobjects.interfaces.Updatable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Object that can hold Updatable objects.
 * Will try to update every object in its update list each time update is called on the object.
 * Used by objects that handles the updating of Updatable objects in the game.
 * Handles the isDead() method on Updatable Objects and disposes of them during next update.
 * Handles adding of new objects, appends new objects to the update list before each update.
 */
public class Updater implements Serializable{
    private ArrayList<Updatable> updateObjects;
    private ArrayList<Updatable> appendObjects;
    private ArrayList<Updatable> updateObjectsToBeDeleted;

    public Updater(){
        this.appendObjects = new ArrayList<>();
        this.updateObjects = new ArrayList<>();
        this.updateObjectsToBeDeleted = new ArrayList<>();
    }

    public void addToUpdateList(Updatable object){
        this.appendObjects.add(object);
    }

    public void addToBeDeletedList(Updatable object){
        updateObjectsToBeDeleted.add(object);
    }

    public ArrayList<Updatable> getUpdateObjects(){
        return updateObjects;
    }

    public void update(){
        updateObjects.removeAll(updateObjectsToBeDeleted);
        updateObjects.addAll(appendObjects);
        appendObjects.clear();
        updateObjects.trimToSize();

        for (Updatable updateObject:
                updateObjects) {
            if (updateObject.isDead()){addToBeDeletedList(updateObject);}
            else updateObject.update();
        }
    }
}
