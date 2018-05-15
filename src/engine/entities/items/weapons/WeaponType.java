package engine.entities.items.weapons;

import engine.entities.gameobjects.Sprite;

/**
 * Enum list of each weapon type implemented.
 * Display name is used for rendering the name into the user interface.
 */
public enum WeaponType {
    BASIC_KNIFE("Knife", new Sprite(49)),
    BASIC_GUN("Pistol", new Sprite(50)),
    MACHINE_GUN("Machine Gun", new Sprite(51)),
    SHOT_GUN("ShotGun", new Sprite(53)),
    GRANADE("Granade", new Sprite(1)),
    TWO_HANDED_GUN("Split Gun", new Sprite(51)),
    TWO_HANDED_MACHINEGUN("Two handed Machine Gun", new Sprite(1)),
    ZOMBIE_ATTACK("Zombie Attack", new Sprite(1));

    private String displayName;
    private Sprite sprite;

    WeaponType(String displayName, Sprite sprite){
        this.displayName = displayName;
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public String getDisplayName() { return displayName; }

}
