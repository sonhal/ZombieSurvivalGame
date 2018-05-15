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

public class InventoryComponent extends ScriptableComponent {

    private Updater updater;
    private WeaponComponent weaponComponent;

    public InventoryComponent(Updater updater) {
        this.updater = updater;
    }

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

    private void pickupItem(GameObject gameObject, Item item){
        if (item instanceof DroppedWeapon){
            addWeapon((DroppedWeapon)item);
        }else if (item instanceof HealthPotion){
            gameObject.getComponentByType(HealthComponent.class)
                    .ifPresent(scriptableComponent ->
                            ((KillableHealthComponent)scriptableComponent).heal(50)) ;
        }
    }

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
