package engine.entities.components;

import engine.controllers.Direction;
import engine.entities.components.ComponentEvent.*;
import engine.entities.components.interfaces.TransformComponent;
import engine.entities.components.interfaces.WeaponComponent;
import engine.entities.gameobjects.interfaces.GameObject;
import engine.entities.items.weapons.Weapon;

/**
 * Can hold and manage a single Weapon instance.
 */
public class SingleWeaponComponent extends WeaponComponent {

    protected Weapon activeWeapon;
    private Direction attackEvent;

    public SingleWeaponComponent(){
        this.activeWeapon = null;
    }


    public void attack(Direction direction, TransformComponent transformComponent, GameObject gameObject){
        if (activeWeapon != null){
            if(activeWeapon.activate(transformComponent.getCurrentTile(), direction)){
                sendMessageToAllComponents(gameObject.getComponents(),
                        new AttackCompletedEvent());
            }
        }
    }

    public void setActiveWeapon(Weapon weapon) {
        this.activeWeapon = weapon;
    }


    @Override
    public void update(GameObject gameObject) {
        if(attackEvent != null){
            //Attack in direction StaticGameObject is facing
            attack(gameObject.getTransformComponent().getFacingDirection(),
                    gameObject.getTransformComponent() , gameObject);
            attackEvent = null;
        }
    }

    @Override
    public void handle(ComponentEvent event) {
        if(event instanceof AttackEvent){
            attackEvent = ((AttackEvent)event).getAttackDirection();
        }
        if(event instanceof PickUpWeaponEvent){
            if (((PickUpWeaponEvent)event).getWeapon() != null){
                setActiveWeapon(((PickUpWeaponEvent)event).getWeapon());
            }
        }
    }

    @Override
    public void innit(GameObject gameObject) {

    }

    @Override
    public void cleanUp(GameObject gameObject) {

    }

    @Override
    public Weapon getActiveWeapon() {
        return activeWeapon;
    }
}
