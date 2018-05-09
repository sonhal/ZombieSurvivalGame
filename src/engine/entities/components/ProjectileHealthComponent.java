package engine.entities.components;

import engine.entities.components.ComponentEvent.*;
import engine.entities.components.interfaces.HealthComponent;
import engine.entities.gameobjects.UpdatableGameObject;
import engine.entities.gameobjects.interfaces.GameObject;
import engine.entities.gameobjects.interfaces.IUpdatableGameObject;

public class ProjectileHealthComponent extends HealthComponent {

    private int range;

    public ProjectileHealthComponent(int range){
        this.range = range;
    }

    @Override
    public void update(GameObject gameObject) {
        if(range <= 0){
            sendMessageToAllComponents(gameObject.getComponents(), new DeathEvent());
            if(gameObject instanceof IUpdatableGameObject){
                UpdatableGameObject uo = (UpdatableGameObject)gameObject;
                uo.die();
            }
        }
    }

    @Override
    public void handle(ComponentEvent event) {
        if(event instanceof HitEvent){
            range = 0;
        }
        if(event instanceof MoveEvent){
            range--;
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
