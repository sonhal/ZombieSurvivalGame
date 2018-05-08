package engine.entities.components;

import engine.entities.components.ComponentEvent.*;
import engine.entities.gameobjects.UpdatableGameObject;
import engine.entities.components.componentEventHandlers.Message;
import engine.entities.gameobjects.interfaces.IGameObject;
import engine.entities.gameobjects.interfaces.IUpdatableGameObject;

public class ProjectileHealthComponent extends ScriptableComponent{

    private int range;

    public ProjectileHealthComponent(int range){
        super(ComponentType.HEALTH_COMPONENT);
        this.range = range;
    }

    @Override
    public void update(IGameObject gameObject) {
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
    public void innit(IGameObject gameObject) {
        //Do nothing
    }

    @Override
    public void cleanUp(IGameObject gameObject) {
        //Do nothing
    }
}
