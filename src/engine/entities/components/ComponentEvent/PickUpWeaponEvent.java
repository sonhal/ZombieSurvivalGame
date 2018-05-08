package engine.entities.components.ComponentEvent;

import engine.entities.items.weapons.Weapon;

public class PickUpWeaponEvent implements ComponentEvent{

    private final Weapon weapon;

    public PickUpWeaponEvent(final Weapon weapon) {
        this.weapon = weapon;
    }

    public Weapon getWeapon() {
        return weapon;
    }
}
