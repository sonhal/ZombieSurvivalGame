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
        ArrayList<ScriptableComponent> components = new ArrayList<>();

        /*
        Setting the sprites for a player, the numbering is not intuitive
        */
        for (int i = 4; i <= 7; i++){
            playerSprites.add(new Sprite(i));
        }
        for (int i = 12; i <= 15; i++){
            playerSprites.add(new Sprite(i));
        }

        components.add(new CollisionComponent());
        components.add(new HealthComponent(1000));
        components.add(new UpdatableTransformComponent(startTile));
        components.add(new PlayerInputComponent(eventHandler, 0));

        UpdatableGraphicsComponent gc = new UpdatableGraphicsComponent(playerSprites.get(0), 300);
        WeaponComponent wc = new WeaponComponent();
        wc.setWeapon(new Gun(updater,10,40, new AttackComponent(20)));
        components.add(wc);

        for (Sprite sprite:
                playerSprites) {
            gc.addSprite(sprite);
        }

        components.add(gc);
        components.add(new AudioComponent(100, Sound.HIT_1));

        ImpUpdatableGameObject player = new ImpUpdatableGameObject(components);
        player.setAsPlayer(true);
        return player;
    }
}
