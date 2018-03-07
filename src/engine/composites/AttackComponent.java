package engine.composites;

import engine.Direction;
import engine.Weapon;

public class AttackComponent implements Component{

    private Weapon weapon;
    private TransformComponent transformComponent;

    public AttackComponent(TransformComponent transformComponent){
        this.weapon = null;
        this.transformComponent = transformComponent;

    }

    public void attack(Direction direction){
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
