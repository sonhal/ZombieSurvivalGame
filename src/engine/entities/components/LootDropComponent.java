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
        Random random = new Random();
        int i = random.nextInt(100);
        if (i < 50){
            return new DroppedWeapon(WeaponType.BASIC_GUN ,new Sprite(2), "Gun", 40);
        }
        else {
            return new HealthPotion(new Sprite(36), "HealthPotion", 100);
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
