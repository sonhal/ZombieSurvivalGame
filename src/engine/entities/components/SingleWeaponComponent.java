package engine.entities.components;

import engine.controllers.Direction;
import engine.entities.components.ComponentEvent.*;
import engine.entities.components.interfaces.TransformComponent;
import engine.entities.components.interfaces.WeaponComponent;
import engine.entities.gameobjects.interfaces.GameObject;
import engine.entities.items.weapons.Weapon;

public class SingleWeaponComponent extends WeaponComponent {

    private Weapon weapon;
    private Direction attackEvent;

    public SingleWeaponComponent(){
        this.weapon = null;
    }


    public void attack(Direction direction, TransformComponent transformComponent, GameObject gameObject){
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
            setWeapon(((PickUpWeaponEvent)event).getWeapon());
        }
    }

    @Override
    public void innit(GameObject gameObject) {

    }

    @Override
    public void cleanUp(GameObject gameObject) {

    }
}
