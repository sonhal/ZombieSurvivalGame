package engine.entities.items.loot;
import engine.entities.gameobjects.Sprite;
import engine.entities.items.Item;

public class DroppedWeapon extends Item {

    protected double activateDelay;
    protected int range;
    protected int damage;
    protected int ammo;

    public DroppedWeapon(Sprite sprite, String name, double activateDelay, int range, int damage, int ammo){
        super(sprite, name);
        this.activateDelay = activateDelay;
        this.range = range;
        this.damage = damage;
        this.ammo = ammo;
    }

    public double getActivateDelay() {
        return activateDelay;
    }

    public int getRange() {
        return range;
    }

    public int getDamage() {
        return damage;
    }

    public int getAmmo() { return ammo;}
}
