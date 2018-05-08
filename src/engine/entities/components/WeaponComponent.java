package engine.entities.components;

import engine.controllers.Direction;
import engine.entities.components.ComponentEvent.*;
import engine.entities.gameobjects.interfaces.IGameObject;
import engine.entities.items.weapons.Weapon;

public class WeaponComponent extends ScriptableComponent{

    private Weapon weapon;
    private Direction attackEvent;

    public WeaponComponent(){
        super(ComponentType.WEAPON_COMPONENT);
        this.weapon = null;
    }


    public void attack(Direction direction, TransformComponent transformComponent, IGameObject gameObject){
        if (weapon != null){
            if(weapon.activate(transformComponent.getCurrentTile(), direction)){
                sendMessageToAllComponents(gameObject.getComponents(),
                        new AttackCompletedEvent());
            }
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


                TransformComponent tc = (TransformComponent)
                        getComponentByType(gameObject.getComponents(), ComponentType.TRANSFORM_COMPONENT).get();

                //Shoot in direction Player is facing
                attack(tc.getFacingDirection(), tc , gameObject);
            }
            attackEvent = null;
        }
    }

    @Override
    public void handle(ComponentEvent event) {
        if(event instanceof AttackEvent){
            attackEvent = ((AttackEvent)event).getAttackDirection();
        }
        if(event instanceof PickUpWeaponEvent){
            setWeapon(((PickUpWeaponEvent)event).getWeapon());
        }
    }

    @Override
    public void innit(IGameObject gameObject) {

    }

    @Override
    public void cleanUp(IGameObject gameObject) {

    }
}
