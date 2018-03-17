package engine;


import engine.composites.GraphicsComponent;
import engine.composites.HealthComponent;
import engine.composites.TransformComponent;

/**
 * Represents a Object in the game that can move and interact with the game environment.
 */
public abstract class LivingObject extends GameObject{

    private HealthComponent healthComponent;

    public LivingObject(TransformComponent tc, GraphicsComponent gc, HealthComponent hc) {
        super(tc, gc);
        this.healthComponent = hc;
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





}
