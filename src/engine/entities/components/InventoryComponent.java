package engine.entities.components;

import engine.controllers.Updater;
import engine.entities.components.ComponentEvent.ComponentEvent;
import engine.entities.components.interfaces.HealthComponent;
import engine.entities.components.interfaces.WeaponComponent;
import engine.entities.gameobjects.interfaces.GameObject;
import engine.entities.items.Item;
import engine.entities.items.loot.DroppedWeapon;
import engine.entities.items.loot.HealthPotion;
import engine.entities.items.weapons.Gun;
import engine.entities.items.weapons.ShootGun;
import engine.entities.items.weapons.TwoHandedGun;
import engine.entities.items.weapons.Weapon;
import engine.services.audio.Sound;

import java.util.Optional;

/**
 * Class handeling the players inventory and the generation of the items the player is using.
 */
public class InventoryComponent extends ScriptableComponent {

    private Updater updater;
    private WeaponComponent weaponComponent;

    /**
     * Constructor assigning variables.
     * @param updater Refference to the updater which makes us able to add new elements such as bullets to the update loop.
     */
    public InventoryComponent(Updater updater) {
        this.updater = updater;
    }

    /**
     * For each game tick checks if there is any game objects on the ground to pick up at the players location.
     * @param gameObject, StaticGameObject that calls the Component
     */
    @Override
    public void update(GameObject gameObject) {
        if(gameObject.getTransformComponent().getCurrentTile().getItem() != null){
            pickupItem(gameObject, gameObject.getTransformComponent().getCurrentTile().getItem());
            gameObject.getTransformComponent().getCurrentTile().setItem(null);
        }
    }

    @Override
    public void handle(ComponentEvent event) {

    }

    @Override
    public void innit(GameObject gameObject) {
        gameObject.getComponentByType(WeaponComponent.class).ifPresent(scriptableComponent -> setWeaponComponent((WeaponComponent) scriptableComponent));
    }

    private void setWeaponComponent(WeaponComponent weaponComponent){
        this.weaponComponent = weaponComponent;
    }

    @Override
    public void cleanUp(GameObject gameObject) {

    }

    /**
     * Method which is triggered when the player steps on a item.
     * @param gameObject The refference to the player object
     * @param item The item which has been picked up from the gound.
     */
    private void pickupItem(GameObject gameObject, Item item){
        if (item instanceof DroppedWeapon){
            addWeapon((DroppedWeapon)item);
        }else if (item instanceof HealthPotion){
            gameObject.getComponentByType(HealthComponent.class)
                    .ifPresent(scriptableComponent ->
                            ((KillableHealthComponent)scriptableComponent).heal(50)) ;
        }
    }

    /**
     * Checks if the pickd up weapon exsists, and decides if the player already has the weapon and there is only ammo to be added or a new weapon should be given to the player.
     * @param weapon Picked up weapon.
     */
    private void addWeapon(DroppedWeapon weapon){
        if(weaponComponent != null){
            if(weaponComponent instanceof MultiWeaponComponent){
                Optional<Weapon> weaponExsists = ((MultiWeaponComponent)weaponComponent).getWeaponList().stream().filter(inventoryWeapon -> inventoryWeapon.getWeaponType() == weapon.getWeaponType()).findFirst();
                if (weaponExsists.isPresent()){
                    weaponExsists.get().setAmmo(weaponExsists.get().getAmmo() + weapon.getAmmo());
                }else{
                    ((MultiWeaponComponent)weaponComponent).addWeapon(makeWeapon(weapon));
                }
            }
        }
    }

    /**
     * Checks the enum type of the picked up weapon, and makes a new weapon object based on the weapon type.
     * @param droppedWeapon The weaponItem which have been picked up
     * @return Returns a weaponObject.
     */
    Weapon makeWeapon(DroppedWeapon droppedWeapon){
        switch (droppedWeapon.getWeaponType()){
            case BASIC_GUN: return new Gun(droppedWeapon.getWeaponType(), new SoundEffectComponent(100, Sound.HIT_1), new SingleAttackComponent(80), updater, 1000, 2, 40);
            case MACHINE_GUN: return new Gun(droppedWeapon.getWeaponType(), new SoundEffectComponent(100, Sound.HIT_1), new SingleAttackComponent(60), updater, 100, 4, 70);
            case TWO_HANDED_GUN: return new TwoHandedGun(droppedWeapon.getWeaponType(), new SoundEffectComponent(100, Sound.HIT_1), new SingleAttackComponent(80), updater, 1000, 2, 40);
            case TWO_HANDED_MACHINEGUN: return new TwoHandedGun(droppedWeapon.getWeaponType(), new SoundEffectComponent(100, Sound.HIT_1), new SingleAttackComponent(50), updater, 200, 2, 40);
            case SHOT_GUN: return new ShootGun(droppedWeapon.getWeaponType(), new SoundEffectComponent(100, Sound.HIT_1), new SingleAttackComponent(80), updater, 500, 4, 12);
            default: return null;
        }
    }
}
