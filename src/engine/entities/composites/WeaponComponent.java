package engine.entities.composites;

import engine.controllers.Direction;
import engine.entities.interfaces.IGameObject;
import engine.entities.interfaces.IUpdatableGameObject;
import engine.entities.items.weapons.Weapon;

import java.io.Serializable;

public class WeaponComponent extends ScriptableComponent{

    private Weapon weapon;
    private Direction attackEvent;

    public WeaponComponent(){
        super(ComponentType.WEAPON_COMPONENT);
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


    @Override
    public void update(IGameObject gameObject) {
        if(attackEvent != null){
            if(getComponentByType(gameObject.getComponents(), ComponentType.TRANSFORM_COMPONENT).isPresent()){
                attack(attackEvent, (TransformComponent)
                        getComponentByType(gameObject.getComponents(), ComponentType.TRANSFORM_COMPONENT).get());
            }
        }
    }

    @Override
    public void handle(Message message) {
        if(message.event == ComponentEvent.ATTACK_EVENT){
            attackEvent = (Direction)message.message;
        }
        if(message.event == ComponentEvent.PICK_UP_EVENT){
            if(message.message instanceof Weapon){
                setWeapon((Weapon)message.message);
            }
        }
    }

    @Override
    public void innit(IGameObject gameObject) {

    }
}
