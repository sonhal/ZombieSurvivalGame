package engine.entities.composites;

import engine.controllers.Direction;
import engine.entities.items.weapons.Weapon;

public class WeaponComponent implements Component{

    private Weapon weapon;

    public WeaponComponent(){
        this.weapon = null;
    }

    public void attack(Direction direction, TransformComponent transformComponent){
        if (weapon != null){
            weapon.activate(transformComponent.getCurrentTile(), direction);
        }
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Weapon getWeapon() {
        return weapon;
    }
}
