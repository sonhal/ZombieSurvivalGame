package engine.controllers;

import engine.entities.interfaces.Updatable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Object that can hold Updatable objects.
 * Will try to update every object i its update list each game tick.
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
