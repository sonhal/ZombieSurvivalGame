package engine.entities.interfaces;

import engine.entities.composites.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Interface describing a IUpdatableGameObject and the expected functionality it will implement.
 * A Object implementing this interface can be added to a Updater.
 */
public interface IUpdatableGameObject extends IGameObject, Updatable,Serializable {

    /**
     * Signals to the Handler of the Object that the Object can be deleted.
     * @return boolean, should the Object be deleted.
     */
    boolean isDead();

    /**
     * Method that should result in the IsDead() method returning true.
     * Any Object clean-up should be done here.
     */
    void die();

    /**
     * Method called by the Updater Object holding a reference to this object.
     * Should call relevant code the object should run each game tick.
     */
    void update();

    Optional<ScriptableComponent> getComponentByType(ComponentType type);
}
