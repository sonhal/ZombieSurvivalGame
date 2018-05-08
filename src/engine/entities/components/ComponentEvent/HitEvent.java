package engine.entities.components.ComponentEvent;
import java.security.InvalidParameterException;

public class HitEvent implements ComponentEvent {

    private final int hitDamage;

    public HitEvent(final int hitDamage){
        this.hitDamage = hitDamage;
        if(hitDamage < 1){
            throw new InvalidParameterException("hitDamage cannot be below 1");
        }
    }

    public int getHitDamage() {
        return hitDamage;
    }
}
