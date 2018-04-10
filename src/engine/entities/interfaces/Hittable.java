package engine.entities.interfaces;

/**
 * Any Object that should be hittable in the game world should implement this Interface
 */
public interface Hittable {

    /**
     * Hit the Object
     * @param hitDamage damage to apply to the object
     */
    void hit(int hitDamage);

    /**
     * @return boolean: Is the Object hit
     */
    boolean isHit();
}
