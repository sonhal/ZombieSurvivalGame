package engine.entities.interfaces;

import engine.entities.composites.*;

/**
 * Interface describing a IUpdatableGameObject and the expected functionality it will implement.
 * A Object implementing this interface can be added to a Updater.
 */
public interface IUpdatableGameObject extends IGameObject, Updatable {

    /**
     * Signals to the Handler of the Object that the Object can be deleted.
     * @return boolean, should the Object be deleted.
     */
    public boolean isDead();

    /**
     * Method that should result in the IsDead() method returning true.
     * Any Object clean-up should be done here.
     */
    public void die();

    /**
     * Method called by the Updater Object holding a reference to this object.
     * Should call relevant code the object should run each game tick.
     */
    public abstract void update();

    /**
     * Access methods that needs to be implemented for the Object to function
     * properly with other Objects accepting a IUpdatableGameObject
     */
    public AvatarGraphicsComponent getGraphicsComponent();

    public AvatarTransformComponent getTransformComponent();

    public InputComponent getInputComponent();

    public CollisionComponent getCollisionComponent();

    public void setTransformComponent(AvatarTransformComponent avatarTransformComponent);

    public void setGraphicsComponent(AvatarGraphicsComponent graphicsComponent);
}
