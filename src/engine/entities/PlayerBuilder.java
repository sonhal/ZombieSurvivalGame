package engine.entities;

import engine.controllers.EventHandler;
import engine.controllers.Updater;
import engine.entities.composites.*;
import engine.entities.interfaces.IUpdatableGameObject;
import engine.entities.items.weapons.Gun;
import engine.entities.world.Tile;

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
        components.add(new HealthComponent(health));
        components.add(new AvatarTransformComponent(startTile));
        components.add(new AvatarInputComponent(eventHandler, 0));

        AvatarGraphicsComponent gc = new AvatarGraphicsComponent(playerSprites.get(0), 300);
        WeaponComponent wc = new WeaponComponent();
        wc.setWeapon(new Gun(updater,10,10,100));
        components.add(wc);

        for (Sprite sprite:
                playerSprites) {
            gc.addSprite(sprite);
        }

        components.add(gc);
        components.add(new AudioComponent());

        ImpUpdatableGameObject player = new ImpUpdatableGameObject(components);
        player.setAsPlayer(true);
        return player;
    }
}
