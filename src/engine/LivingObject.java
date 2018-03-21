package engine;


import engine.composites.GraphicsComponent;
import engine.composites.HealthComponent;
import engine.composites.TransformComponent;

/**
 * Represents a Object in the game that can move and interact with the game environment.
 */
public abstract class LivingObject extends GameObject{

    private HealthComponent healthComponent;
    private double timeHitted; //millis
    private double TIME_HIT = 500; //millis


    public LivingObject(TransformComponent tc, GraphicsComponent gc, HealthComponent hc) {
        super(tc, gc);
        this.healthComponent = hc;
    }

    public int getHealth(){
        return healthComponent.getHealthAmount();
    }

    public void damage(int damageToInflict){
        healthComponent.damage(damageToInflict);
    }

    public void heal(int healAmount){
        healthComponent.heal(healAmount);
    }

    public boolean isAlive(){
        return healthComponent.isAlive();
    }

    @Override
    public void hit(int hitAmount) {
        damage(hitAmount);
        setIsHit(true);
        timeHitted = System.currentTimeMillis();
    }

    public void updateHitStatus(){
        if(timeHitted + TIME_HIT < System.currentTimeMillis()) setIsHit(false);
    }

}
