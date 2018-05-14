package engine.entities.components;

import engine.entities.components.ComponentEvent.ComponentEvent;
import engine.entities.components.ComponentEvent.DeathEvent;
import engine.entities.gameobjects.Sprite;
import engine.entities.gameobjects.interfaces.GameObject;
import engine.entities.items.Item;
import engine.entities.items.loot.DroppedWeapon;
import engine.entities.items.loot.HealthPotion;
import engine.entities.items.weapons.WeaponType;

import java.util.Random;

public class LootDropComponent extends ScriptableComponent{

    private boolean isDead;

    private Item lootFactory(){
        double i = Math.random() * 100;
        if (i < 10){
            return new DroppedWeapon(WeaponType.BASIC_GUN ,new Sprite(2), "Gun", 10);
        }
        else if (i > 10 && i< 20){
            return new HealthPotion(new Sprite(36), "HealthPotion", 100);
        }else if (i > 20 && i < 30) {
            return new DroppedWeapon(WeaponType.TWO_HANDED_GUN, new Sprite(2), "Two Handed Gun", 80);
        }else if (i > 30){
            return new DroppedWeapon(WeaponType.SHOT_GUN,new Sprite(2),"ShotGun",30);
        }else{
                return null;

        }

    }

    @Override
    public void update(GameObject gameObject) {
        if (isDead){
            gameObject.getTransformComponent().getCurrentTile().setItem(lootFactory() );
        }

    }

    @Override
    public void handle(ComponentEvent event) {
        if(event instanceof DeathEvent){
            isDead = true;
        }
    }

    @Override
    public void innit(GameObject gameObject) {

    }

    @Override
    public void cleanUp(GameObject gameObject) {

    }
}
