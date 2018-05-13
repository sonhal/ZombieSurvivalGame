package engine.entities.components;

import engine.entities.components.ComponentEvent.ChangeWeaponEvent;
import engine.entities.components.ComponentEvent.ComponentEvent;
import engine.entities.components.ComponentEvent.MoveEvent;
import engine.entities.gameobjects.interfaces.GameObject;
import engine.entities.items.Item;
import engine.entities.items.weapons.MeleeWeapon;
import engine.entities.items.weapons.Weapon;

import java.util.ArrayList;
import java.util.List;

public class InventoryComponent extends ScriptableComponent {

    List<Weapon> weaponsinventory = new ArrayList<>();
    Weapon currentWeapon;
    int selectedWeapon = 0;
    SingleWeaponComponent singleWeaponComponent;

    public InventoryComponent(SingleWeaponComponent singleWeaponComponent, Weapon starterWeapon) {
        this.singleWeaponComponent = singleWeaponComponent;
        weaponsinventory.add(new MeleeWeapon(4, new SingleAttackComponent(1000)));
        weaponsinventory.add(new MeleeWeapon(4, new SingleAttackComponent(1000)));
        weaponsinventory.add(starterWeapon);
        singleWeaponComponent.setWeapon(weaponsinventory.get(0));
    }

    @Override
    public void update(GameObject gameObject) {
        if(gameObject.getTransformComponent().getCurrentTile().getItem() != null){
            pickupItem(gameObject.getTransformComponent().getCurrentTile().getItem());
            gameObject.getTransformComponent().getCurrentTile().setItem(null);
        }
    }

    @Override
    public void handle(ComponentEvent event) {
        if(event instanceof ChangeWeaponEvent){
            cycleWeapon();
        }
    }

    @Override
    public void innit(GameObject gameObject) {

    }

    @Override
    public void cleanUp(GameObject gameObject) {

    }

    void pickupItem(Item item){
        if (item instanceof Weapon){
            weaponsinventory.add((Weapon) item);
        }
    }

    void cycleWeapon(){
        System.out.println("Cycling weapons");
        if(selectedWeapon+1 < weaponsinventory.size()){
            selectedWeapon++;
            currentWeapon = weaponsinventory.get(selectedWeapon);
            singleWeaponComponent.setWeapon(weaponsinventory.get(selectedWeapon));
        }else{
            selectedWeapon = 0;
            currentWeapon = weaponsinventory.get(selectedWeapon);
            singleWeaponComponent.setWeapon(weaponsinventory.get(selectedWeapon));
        }
        System.out.println("Weapon " + selectedWeapon + " selected.");
    }



}
