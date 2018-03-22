package engine.composites;

public class HealthComponent {

    private int healthAmount;

    public HealthComponent(int healthAmount){
        this.healthAmount = healthAmount;
    }


    public void damage(int damageToInflict){
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
}
