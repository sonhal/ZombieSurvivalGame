package engine.entities.components;
import engine.entities.components.ComponentEvent.*;
import engine.entities.components.interfaces.HealthComponent;
import engine.entities.gameobjects.UpdatableGameObject;
import engine.entities.gameobjects.interfaces.GameObject;
import engine.entities.gameobjects.interfaces.IUpdatableGameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Component representing the health and health related state like damaged status etc. Being killable means it sends
 * a Death event to the other components and signals the GameObject in the event the health value reaches zero.
 */
public class KillableHealthComponent extends HealthComponent {

    private int healthAmount;
    private boolean isDamaged;
    private boolean isDead = false;
    private double timeDamaged;
    private static final int DAMAGE_TIME = 500;
    private List<Integer> damageToTake;

    public KillableHealthComponent(int healthAmount){
        this.healthAmount = healthAmount;
        this.damageToTake = new ArrayList<>();
    }


    private void damage(int damageToInflict){
        isDamaged = true;
        healthAmount -= damageToInflict;
        timeDamaged = System.currentTimeMillis();
    }

    public void heal(int healAmount){
        if(healthAmount > 0 && healAmount > 0){
            healthAmount += healthAmount;
            if(healthAmount >200){ healthAmount = 200;}
        }
    }

    private void broadcastStatus(GameObject gameObject){
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

    @Override
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
    public void update(GameObject gameObject) {
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
    public void innit(GameObject gameObject) {

    }

    @Override
    public void cleanUp(GameObject gameObject) {

    }
}
