package engine;

import engine.composites.Sprite;

public class WeaponPickup extends Item {

    private Weapon weapon;

    public WeaponPickup(Sprite sprite, int damage, int range) {
        super(sprite);
        this.weapon = new Weapon(range, damage);
    }


    @Override
    public Weapon pickup() {
        return this.weapon;
    }
}
