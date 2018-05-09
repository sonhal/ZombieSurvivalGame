package engine.entities.gameobjects;

import engine.controllers.EventHandler;
import engine.controllers.Updater;
import engine.entities.components.*;
import engine.entities.gameobjects.interfaces.IUpdatableGameObject;
import engine.entities.items.weapons.Gun;
import engine.world.Tile;
import engine.services.audio.Sound;

import java.util.ArrayList;

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

        SingleWeaponComponent wc = new SingleWeaponComponent();
        wc.setWeapon(new Gun(updater,10,40, new SingleAttackComponent(20)));



        IUpdatableGameObject player = new UpdatableGameObject.Builder(new UpdatableTransformComponent(startTile))
                .addComponent(new PlayerInputComponent(eventHandler, 0))
                .addComponent(new GameObjectCollisionComponent())
                .addComponent(new KillableHealthComponent(1000))
                .addComponent(gc)
                .addComponent(wc)
                .addComponent(new SoundEffectComponent(100, Sound.HIT_1))
                .build();


        player.setAsPlayer(true);
        return player;
    }
}