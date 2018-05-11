package engine.entities.items;

import engine.entities.gameobjects.Sprite;
import engine.entities.items.weapons.Weapon;

/**
 * Iterm in the game world that the Player can interact with.
 * Interaction from Player results in the Player picking up the contained Weapon
 */
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
