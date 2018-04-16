package engine.entities.composites;
import engine.entities.interfaces.IGameObject;
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
    }

    @Override
    public void handle(Message message) {
        if(message.type == ComponentType.ATTACK_COMPONENT){
            damageToTake.add((int)message.message);
        }
    }

    @Override
    public void innit(IGameObject gameObject) {

    }
}
