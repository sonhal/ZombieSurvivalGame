package engine.entities;


import engine.entities.GameObject;
import engine.entities.composites.GraphicsComponent;
import engine.entities.composites.HealthComponent;
import engine.entities.composites.Sprite;
import engine.entities.composites.TransformComponent;

/**
 * Represents a Object in the game that can move and interact with the game environment.
 */
public abstract class LivingObject extends GameObject {

    private HealthComponent healthComponent;
    private double timeHitted; //millis
    private double TIME_HIT = 500; //millis


    public LivingObject(GraphicsComponent gc, HealthComponent hc) {
        super(gc);
        this.healthComponent = hc;
    }

    LivingObject(Sprite sprite, int health){
        super(sprite);
        this.healthComponent = new HealthComponent(health);
    }

    public LivingObject(Sprite sprite){
        super(sprite);
        this.healthComponent = new HealthComponent(1);
    }



    public int getHealth(){
        return healthComponent.getHealthAmount();
    }

    private void damage(int damageToInflict){
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
