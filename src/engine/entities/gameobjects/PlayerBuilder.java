package engine.entities.gameobjects;

import engine.controllers.EventHandler;
import engine.controllers.Updater;
import engine.entities.components.*;
import engine.entities.gameobjects.interfaces.IUpdatableGameObject;
import engine.entities.items.weapons.Knife;
import engine.world.Tile;
import engine.services.audio.Sound;

import java.util.ArrayList;

/**
 * Builds the player GameObject. Is usually only called at the start of a new game.
 */
public class PlayerBuilder {

    public static IUpdatableGameObject create(Updater updater, EventHandler eventHandler, int health, Tile startTile){
        ArrayList<Sprite> playerSprites = new ArrayList<>();
        /*
        Setting the sprites for a player, the numbering is not intuitive
        */
        for (int i = 4; i <= 7; i++){
            playerSprites.add(new Sprite(i));
        }
        for (int i = 12; i <= 15; i++){
            playerSprites.add(new Sprite(i));
        }

        UpdatableGraphicsComponent gc = new UpdatableGraphicsComponent(playerSprites.get(0), 300);
        for (Sprite sprite:
                playerSprites) {
            gc.addSprite(sprite);
        }

        InventoryComponent inventoryComponent = new InventoryComponent(updater);
        MultiWeaponComponent multiWeaponComponent = new MultiWeaponComponent();
        multiWeaponComponent.setActiveWeapon(new Knife(new AttackSoundEffectComponent(100, Sound.KNIFE_ATTACK), new SingleAttackComponent(40), updater, 200));

        IUpdatableGameObject player = new UpdatableGameObject.Builder(new UpdatableTransformComponent(startTile))
                .addComponent(new PlayerInputComponent(eventHandler, 0))
                .addComponent(new GameObjectCollisionComponent())
                .addComponent(new KillableHealthComponent(200))
                .addComponent(new PickupItemSoundEffectComponent(100, Sound.PICKUP_WEAPON, Sound.PICKUP_HEALTH))
                .addComponent(gc)
                .addComponent(inventoryComponent)
                .addComponent(multiWeaponComponent)
                .build();

        player.setAsPlayer(true);
        return player;
    }
}
