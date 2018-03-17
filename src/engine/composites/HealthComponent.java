package engine.composites;

public class HealthComponent {

    private int healthAmount;

    public HealthComponent(int healthAmount){
        this.healthAmount = healthAmount;
    }


    public void damage(int damageToInflict){
        healthAmount =- damageToInflict;

    }

    public void heal(int healAmount){
        if(healthAmount > 0 && healAmount > 0){
            healAmount += healthAmount;
        }
    }


    public boolean isAlive(){
        if (healthAmount < 1) return false;
        else return true;
    }

}
