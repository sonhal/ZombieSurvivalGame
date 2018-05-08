package engine.entities.components;
import engine.entities.components.ComponentEvent.*;
import engine.entities.gameobjects.UpdatableGameObject;
import engine.entities.components.componentEventHandlers.Message;
import engine.entities.gameobjects.interfaces.IGameObject;
import engine.entities.gameobjects.interfaces.IUpdatableGameObject;

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
            sendMessageToAllComponents(gameObject.getComponents(), new DamageTakenEvent(healthAmount));
        }
        if(!isAlive()){
            sendMessageToAllComponents(gameObject.getComponents(), new DeathEvent());
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
        damageToTake.clear();
    }

    @Override
    public void update(IGameObject gameObject) {
        takeDamage();
        updateDamagedStatus();
        broadcastStatus(gameObject);
    }

    @Override
    public void handle(ComponentEvent event) {
        if(event instanceof HitEvent){
            damageToTake.add(((HitEvent) event).getHitDamage());
        }
    }

    @Override
    public void innit(IGameObject gameObject) {

    }

    @Override
    public void cleanUp(IGameObject gameObject) {

    }
}
