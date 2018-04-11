package engine.entities;

import engine.controllers.EventHandler;
import engine.controllers.GameHandler;
import engine.controllers.Updater;
import engine.entities.Avatar;
import engine.entities.composites.*;
import engine.entities.interfaces.Updatable;
import engine.entities.items.weapons.Gun;

import java.util.ArrayList;

public class PlayerFactory {


    public static Avatar create(Updater updater, EventHandler eventHandler, int health){
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

        WeaponComponent wc = new WeaponComponent();
        CollisionComponent cc = new CollisionComponent();
        HealthComponent hc = new HealthComponent(health);
        AvatarTransformComponent tc = new AvatarTransformComponent(0,300);
        AvatarInputComponent ic = new AvatarInputComponent(eventHandler, tc, wc);
        AvatarGraphicsComponent gc = new AvatarGraphicsComponent(playerSprites.get(0));

        for (Sprite sprite:
                playerSprites) {
            gc.addSprite(sprite);
        }
        Avatar player = new Avatar(gc, hc, wc, cc,ic, tc);
        player.pickupWeapon(new Gun(updater,10,10,200));
        player.setAsPlayer(true);
        return player;
    }
}
