package engine.entities;

public interface Hittable {

    void hit(int hitDamage);
    boolean isHit();
}
