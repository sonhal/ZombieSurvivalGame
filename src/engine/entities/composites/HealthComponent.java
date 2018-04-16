package engine.entities.composites;

import java.io.Serializable;

public class HealthComponent implements Serializable{

    private int healthAmount;
    private boolean isDamaged;
    private double timeDamaged;
    private static final int DAMAGE_TIME = 500;

    public HealthComponent(int healthAmount){
        this.healthAmount = healthAmount;
    }


    public void damage(int damageToInflict){
        isDamaged = true;
        healthAmount -= damageToInflict;
        timeDamaged = System.currentTimeMillis();
    }

    public void heal(int healAmount){
        if(healthAmount > 0 && healAmount > 0){
            healthAmount += healthAmount;
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

    public void setIsDamaged(boolean isDamaged){
        this.isDamaged = isDamaged;
    }

    public void updateDamagedStatus(){
        if(TimeComponent.canUpdate(DAMAGE_TIME, timeDamaged)) setIsDamaged(false);
    }
}
