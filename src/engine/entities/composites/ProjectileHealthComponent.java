package engine.entities.composites;

import engine.entities.UpdatableGameObject;
import engine.entities.interfaces.IGameObject;
import engine.entities.interfaces.IUpdatableGameObject;

public class ProjectileHealthComponent extends ScriptableComponent{

    private int range;

    public ProjectileHealthComponent(int range){
        super(ComponentType.HEALTH_COMPONENT);
        this.range = range;
    }

    @Override
    public void update(IGameObject gameObject) {

        if(range <= 0){
            sendMessageToAllComponents(gameObject.getComponents(), new Message(ComponentEvent.DEATH_EVENT, true));
            if(gameObject instanceof IUpdatableGameObject){
                UpdatableGameObject uo = (UpdatableGameObject)gameObject;
                uo.die();
            }
        }
    }

    @Override
    public void handle(Message message) {
        if(message.event == ComponentEvent.COLLISION_EVENT){
            range = 0;
        }
        if(message.event == ComponentEvent.MOVE_EVENT){
            range--;
        }
    }

    @Override
    public void innit(IGameObject gameObject) {

    }

    @Override
    public void cleanUp(IGameObject gameObject) {

    }
}
