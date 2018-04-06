package engine.entities.composites;

public class HealthComponent {

    private int healthAmount;
    private boolean isDamaged;

    public HealthComponent(int healthAmount){
        this.healthAmount = healthAmount;
    }


    public void damage(int damageToInflict){
        isDamaged = true;
        healthAmount -= damageToInflict;
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
}
