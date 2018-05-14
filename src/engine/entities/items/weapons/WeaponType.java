package engine.entities.items.weapons;

public enum WeaponType {
    BASIC_KNIFE("Rusty Knife"),
    BASIC_GUN("Rusty Gun"),
    MACHINE_GUN("Machine Gun"),
    SHOT_GUN("ShotGun"),
    GRANADE("Grandade"),
    TWO_HANDED_GUN("Two handed Gun"),
    TWO_HANDED_MACHINEGUN("Two handed Machinegun");

    private String displayName;

    WeaponType(String displayName){
        this.displayName = displayName;
    }

    public String displayName() { return displayName; }

}
