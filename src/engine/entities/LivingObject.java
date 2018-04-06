package engine.entities;



import engine.entities.GameObject;
import engine.entities.composites.*;

/**
 * Represents a Object in the game that can move and interact with the game environment.
 */
public abstract class LivingObject extends GameObject implements Hittable{

    private HealthComponent healthComponent;
    private double timeHitted; //millis
    private static final double TIME_HIT = 500; //millis


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

    public void heal(int healAmount){
        healthComponent.heal(healAmount);
    }

    public boolean isAlive(){
        return healthComponent.isAlive();
    }

    @Override
    public void hit(int hitAmount) {
        healthComponent.damage(hitAmount);
        timeHitted = System.currentTimeMillis();
    }

    @Override
    public boolean isHit(){
        return healthComponent.isDamaged();
    }

    public void updateHitStatus(){
        if(TimeComponent.canUpdate(TIME_HIT, timeHitted)) healthComponent.setIsDamaged(false);
    }

}
