package engine.entities.composites;
import engine.entities.UpdatableGameObject;
import engine.entities.interfaces.IGameObject;
import engine.entities.interfaces.IUpdatableGameObject;

import java.util.ArrayList;
import java.util.List;

public class HealthComponent extends ScriptableComponent{

    private int healthAmount;
    private boolean isDamaged;
    private double timeDamaged;
    private static final int DAMAGE_TIME = 500;
    private List<Integer> damageToTake;

    public HealthComponent(int healthAmount){
        super(ComponentType.HEALTH_COMPONENT);
        this.healthAmount = healthAmount;
        this.damageToTake = new ArrayList<>();
    }


    private void damage(int damageToInflict){
        isDamaged = true;
        healthAmount -= damageToInflict;
        timeDamaged = System.currentTimeMillis();
    }

    private void heal(int healAmount){
        if(healthAmount > 0 && healAmount > 0){
            healthAmount += healthAmount;
        }
    }

    private void broadcastStatus(IGameObject gameObject){
        if(isDamaged){
            sendMessageToAllComponents(gameObject.getComponents(), new Message(ComponentEvent.DAMAGE_TAKEN_EVENT, healthAmount));
        }
        if(!isAlive()){
            sendMessageToAllComponents(gameObject.getComponents(), new Message(ComponentEvent.DEATH_EVENT, true));
            if(gameObject instanceof IUpdatableGameObject){
                UpdatableGameObject uo = (UpdatableGameObject)gameObject;
                uo.die();
            }
        }
    }

    public int getHealthAmount() {
        return healthAmount;
    }

    public boolean isAlive(){
        return this.healthAmount > 0;
    }

    public boolean isDamaged(){
        return isDamaged;
    }

    private void setIsDamaged(boolean isDamaged){
        this.isDamaged = isDamaged;
    }

    private void updateDamagedStatus(){
        if(canActivate(DAMAGE_TIME, timeDamaged)) setIsDamaged(false);
    }

    private void takeDamage(){
        for (Integer dmg:
             damageToTake) {
            damage(dmg);
        }
    }

    @Override
    public void update(IGameObject gameObject) {
        takeDamage();
        updateDamagedStatus();
        broadcastStatus(gameObject);
    }

    @Override
    public void handle(Message message) {
        if(message.event == ComponentEvent.HIT_EVENT){
            damageToTake.add((int)message.message);
        }
    }

    @Override
    public void innit(IGameObject gameObject) {

    }

    @Override
    public void cleanUp(IGameObject gameObject) {

    }
}
