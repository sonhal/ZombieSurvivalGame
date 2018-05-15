package engine.entities.items.loot;
import engine.entities.gameobjects.Sprite;
import engine.entities.items.Item;
import engine.entities.items.weapons.Weapon;
import engine.entities.items.weapons.WeaponType;

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
