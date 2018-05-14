package engine.entities.components.interfaces;

import engine.entities.components.ScriptableComponent;
import engine.entities.items.weapons.Weapon;

public abstract class WeaponComponent extends ScriptableComponent {

    public abstract Weapon getActiveWeapon();
}
