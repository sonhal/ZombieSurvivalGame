package engine.entities.components;

import engine.controllers.Direction;
import engine.controllers.Updater;
import engine.entities.components.ComponentEvent.ChangeWeaponEvent;
import engine.entities.components.ComponentEvent.ComponentEvent;
import engine.entities.components.ComponentEvent.MoveEvent;
import engine.entities.components.interfaces.AttackComponent;
import engine.entities.components.interfaces.HealthComponent;
import engine.entities.gameobjects.interfaces.GameObject;
import engine.entities.gameobjects.interfaces.IUpdatableGameObject;
import engine.entities.items.Item;
import engine.entities.items.loot.DroppedWeapon;
import engine.entities.items.loot.HealthPottion;
import engine.entities.items.weapons.Gun;
import engine.entities.items.weapons.Knife;
import engine.entities.items.weapons.Weapon;
import engine.world.Tile;

import java.util.ArrayList;
import java.util.List;

public class InventoryComponent extends ScriptableComponent {

    List<Weapon> weaponsinventory = new ArrayList<>();
    Weapon currentWeapon;
    int selectedWeapon = 0;
    SingleWeaponComponent singleWeaponComponent;
    Updater updater;
    IUpdatableGameObject player;

    List<ScriptableComponent> scriptableComponents;

    public InventoryComponent(SingleWeaponComponent singleWeaponComponent, Updater updater) {
        this.singleWeaponComponent = singleWeaponComponent;
        weaponsinventory.add(new Knife(new SingleAttackComponent(1000), updater, 4, 5  ));
        singleWeaponComponent.setWeapon(weaponsinventory.get(0));
        this.updater = updater;
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
        scriptableComponents = gameObject.getComponents();
    }

    @Override
    public void cleanUp(GameObject gameObject) {

    }



    void pickupItem(Item item){
        if (item instanceof DroppedWeapon){
            weaponsinventory.add(makeWeapon((DroppedWeapon)item));
        }else if (item instanceof HealthPottion){
            player.getComponentByType(HealthComponent.class)
                    .ifPresent(scriptableComponent ->
                            ((KillableHealthComponent)scriptableComponent).heal(50)) ;
        }
    }

    Weapon makeWeapon(DroppedWeapon item){
        return new Gun(new SingleAttackComponent(item.getDamage()), updater, item.getActivateDelay(), item.getRange());
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

    public void setPlayer(IUpdatableGameObject player) {
        this.player = player;
    }
}
