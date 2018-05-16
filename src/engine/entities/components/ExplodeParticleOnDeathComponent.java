package engine.entities.components;

import engine.controllers.Direction;
import engine.controllers.Updater;
import engine.entities.components.ComponentEvent.CollisionEvent;
import engine.entities.components.ComponentEvent.ComponentEvent;
import engine.entities.components.ComponentEvent.DeathEvent;
import engine.entities.gameobjects.GameObjectFactory;
import engine.entities.gameobjects.interfaces.GameObject;

public class ExplodeParticleOnDeathComponent extends ScriptableComponent {


    private boolean isDead;
    private Updater updater;
    private boolean collision;

    public ExplodeParticleOnDeathComponent(Updater updater) {
        this.updater = updater;
    }

    @Override
    public void update(GameObject gameObject) {
        if(isDead && collision){
            updater.addToUpdateList(GameObjectFactory.explosion(gameObject.getTransformComponent().getCurrentTile()
                    .getTileInDirection(gameObject.getTransformComponent().getFacingDirection()), Direction.UP, 10));
        }
        if(isDead && !collision){
            updater.addToUpdateList(GameObjectFactory.explosion(gameObject.getTransformComponent().getCurrentTile(), Direction.UP, 10));
        }
    }

    @Override
    public void handle(ComponentEvent event) {
        if(event instanceof DeathEvent){
            isDead = true;
        }
        if(event instanceof CollisionEvent){
            if( ((CollisionEvent)event).collisonDirection() != null){
                collision = true;
            }
            else {
                collision = false;
            }
        }
    }

    @Override
    public void innit(GameObject gameObject) {
        //Do nothing
    }

    @Override
    public void cleanUp(GameObject gameObject) {
        //Do nothing
    }
}
