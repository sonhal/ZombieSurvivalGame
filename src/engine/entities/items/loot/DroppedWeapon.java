package engine.entities.items.loot;
import engine.entities.items.Item;
import engine.entities.items.weapons.Weapon;
import engine.entities.items.weapons.WeaponType;

/**
 * Representing a weapon as a item on the ground.
 * After picked up there will be created a weapon object based on the stats of this item.
 */
public class DroppedWeapon extends Item {

    protected WeaponType weaponType;
    protected Class<Weapon> weaponClass;
    protected int ammo;

    public DroppedWeapon(WeaponType weaponType, int ammo){
        super(weaponType.getSprite(), weaponType.getDisplayName());
        this.weaponType = weaponType;
        this.ammo = ammo;
    }

    public int getAmmo() { return ammo;}

    public WeaponType getWeaponType() {
        return weaponType;
    }
}
