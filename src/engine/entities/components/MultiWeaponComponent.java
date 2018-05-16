package engine.entities.components;

import engine.entities.components.ComponentEvent.ChangeWeaponEvent;
import engine.entities.components.ComponentEvent.ComponentEvent;
import engine.entities.components.interfaces.WeaponComponent;
import engine.entities.gameobjects.interfaces.GameObject;
import engine.entities.items.weapons.Weapon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * WeaponComponent capable of holding several weapons and cycling through them
 */
public class MultiWeaponComponent extends SingleWeaponComponent {

    private boolean changeWeapon;
    private List<Weapon> weaponList;
    private int cycleCounter;

    public MultiWeaponComponent(){
        weaponList = new ArrayList<>();
    }

    @Override
    public void update(GameObject gameObject) {
        if(changeWeapon){
            if(cycleCounter < weaponList.size() -1){
                cycleCounter++;
                activeWeapon = weaponList.get(cycleCounter);
            }
            else {
                cycleCounter = 0;
                activeWeapon = weaponList.get(cycleCounter);
            }
            changeWeapon = false;
        }
        super.update(gameObject);
    }

    @Override
    public void handle(ComponentEvent event) {
        if(event instanceof ChangeWeaponEvent){
            changeWeapon = true;
        }
        super.handle(event);
    }

    @Override
    public void setActiveWeapon(Weapon weapon) {
        if(weaponList.size() > 4){
            weaponList.remove(0);
        }
        this.weaponList.add(weapon);
        activeWeapon = weapon;
    }

    public void addWeapon(Weapon weapon){
        if(weaponList.size() > 4){
            weaponList.remove(0);
        }
        this.weaponList.add(weapon);
    }

    public List<Weapon> getWeaponList() {
        return weaponList;
    }
}
