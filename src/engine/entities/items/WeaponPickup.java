package engine.entities.items;

import engine.entities.composites.Sprite;
import engine.entities.items.weapons.Weapon;

public class WeaponPickup extends Item {

    private Weapon weapon;

    public WeaponPickup(Sprite sprite, Weapon weapon) {
        super(sprite);
        this.weapon = weapon;
    }


    @Override
    public Weapon pickup() {
        return this.weapon;
    }
}
