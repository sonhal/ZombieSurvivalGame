package engine.entities.items.weapons;

public enum WeaponType {
    BASIC_KNIFE("Knife"),
    BASIC_GUN("Pistol"),
    MACHINE_GUN("Machine Gun"),
    SHOT_GUN("ShotGun"),
    GRANADE("Granade"),
    TWO_HANDED_GUN("Split Gun"),
    TWO_HANDED_MACHINEGUN("Two handed Machine Gun"),
    ZOMBIE_ATTACK("Zombie Attack");

    private String displayName;

    WeaponType(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName() { return displayName; }

}
