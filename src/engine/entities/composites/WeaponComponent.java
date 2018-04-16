package engine.entities.composites;

import engine.controllers.Direction;
import engine.entities.interfaces.IUpdatableGameObject;
import engine.entities.items.weapons.Weapon;

import java.io.Serializable;

public class WeaponComponent implements Component<IUpdatableGameObject>, Serializable{

    private Weapon weapon;

    public WeaponComponent(){
        this.weapon = null;
    }

    @Override
    public void update(IUpdatableGameObject componentHolder) {
        Direction attackDirection = componentHolder.getInputComponent().getAttackEvent();
        if(attackDirection != null){
            attack(componentHolder.getTransformComponent().getFacingDirection(), componentHolder.getTransformComponent());
        }
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
