package view2D;

import engine.entities.gameobjects.Sprite;
import javafx.scene.image.Image;

import java.util.HashMap;

/**
 * Handles the transformation from Sprite objects in the game engine to actual image sprites written
 * to the screen.
 */
public class SpriteTranslationHandler {
    private HashMap<Integer, String> spriteImageFiles;
    private HashMap<Integer, Image> sprites;


    public SpriteTranslationHandler(){

        spriteImageFiles = new HashMap<>();
        sprites = new HashMap<>();

        spriteImageFiles.put(1, "view2D/static/grass_v2.png");
        spriteImageFiles.put(2, "view2D/static/sword.png");
        spriteImageFiles.put(3, "view2D/static/water_bucket.png");
        spriteImageFiles.put(4, "view2D/static/red_still_down.png");
        spriteImageFiles.put(5, "view2D/static/red_still_up.png");
        spriteImageFiles.put(6, "view2D/static/red_still_left.png");
        spriteImageFiles.put(7, "view2D/static/red_still_right.png");
        spriteImageFiles.put(8, "view2D/static/fire_bullet_up.png");
        spriteImageFiles.put(9, "view2D/static/fire_bullet_down.png");
        spriteImageFiles.put(10, "view2D/static/fire_bullet_left.png");
        spriteImageFiles.put(11, "view2D/static/fire_bullet_right.png");
        spriteImageFiles.put(12, "view2D/static/red_walking_half_up.png");
        spriteImageFiles.put(13, "view2D/static/red_walking_half_down.png");
        spriteImageFiles.put(14, "view2D/static/red_walking_half_left.png");
        spriteImageFiles.put(15, "view2D/static/red_walking_half_right.png");
        spriteImageFiles.put(16, "view2D/static/red_walking_half_right.png");
        spriteImageFiles.put(17, "view2D/static/hit_sprite.png");
        spriteImageFiles.put(18, "view2D/static/zombie_still_down.png");
        spriteImageFiles.put(19, "view2D/static/zombie_still_up.png");
        spriteImageFiles.put(20, "view2D/static/zombie_still_left.png");
        spriteImageFiles.put(21, "view2D/static/zombie_still_right.png");
        spriteImageFiles.put(22, "view2D/static/zombie_walking_half_down.png");
        spriteImageFiles.put(23, "view2D/static/zombie_walking_half_up.png");
        spriteImageFiles.put(24, "view2D/static/zombie_walking_half_left.png");
        spriteImageFiles.put(25, "view2D/static/zombie_walking_half_right.png");
        spriteImageFiles.put(26, "view2D/static/dirt.png");
        spriteImageFiles.put(27, "view2D/static/grass_v3.png");
        spriteImageFiles.put(28, "view2D/static/tree.png");
        spriteImageFiles.put(29, "view2D/static/black.png");
        spriteImageFiles.put(30, "view2D/static/explosion_1.png");
        spriteImageFiles.put(31, "view2D/static/explosion_2.png");
        spriteImageFiles.put(32, "view2D/static/explosion_3.png");
        spriteImageFiles.put(33, "view2D/static/explosion_4.png");
        spriteImageFiles.put(34, "view2D/static/explosion_5.png");
        spriteImageFiles.put(35, "view2D/static/explosion_6.png");
        spriteImageFiles.put(36, "view2D/static/healthpottion.png");
        spriteImageFiles.put(37, "view2D/static/knife-up1.png");
        spriteImageFiles.put(38, "view2D/static/knife-up2.png");
        spriteImageFiles.put(39, "view2D/static/knife-up3.png");
        spriteImageFiles.put(40, "view2D/static/knife-down1.png");
        spriteImageFiles.put(41, "view2D/static/knife-down2.png");
        spriteImageFiles.put(42, "view2D/static/knife-down3.png");
        spriteImageFiles.put(43, "view2D/static/knife-left1.png");
        spriteImageFiles.put(44, "view2D/static/knife-left2.png");
        spriteImageFiles.put(45, "view2D/static/knife-left3.png");
        spriteImageFiles.put(46, "view2D/static/knife-right1.png");
        spriteImageFiles.put(47, "view2D/static/knife-right2.png");
        spriteImageFiles.put(48, "view2D/static/knife-right3.png");
        spriteImageFiles.put(49, "view2D/static/knife.png");
        spriteImageFiles.put(50, "view2D/static/pistol.png");
        spriteImageFiles.put(51, "view2D/static/machinegun.png");
        spriteImageFiles.put(52, "view2D/static/canongun.png");
        spriteImageFiles.put(53, "view2D/static/shotgun.png");
        spriteImageFiles.put(54, "view2D/static/batSprite-up1.png");
        spriteImageFiles.put(55, "view2D/static/batSprite-up2.png");
        spriteImageFiles.put(56, "view2D/static/batSprite-up3.png");
        spriteImageFiles.put(57, "view2D/static/batSprite-down1.png");
        spriteImageFiles.put(58, "view2D/static/batSprite-down2.png");
        spriteImageFiles.put(59, "view2D/static/batSprite-down3.png");
        spriteImageFiles.put(60, "view2D/static/batSprite-left1.png");
        spriteImageFiles.put(61, "view2D/static/batSprite-left2.png");
        spriteImageFiles.put(62, "view2D/static/batSprite-left3.png");
        spriteImageFiles.put(63, "view2D/static/batSprite-right1.png");
        spriteImageFiles.put(64, "view2D/static/batSprite-right2.png");
        spriteImageFiles.put(65, "view2D/static/batSprite-right3.png");



        for (Integer key: spriteImageFiles.keySet()
             ) {
            sprites.put(key, getImage(key));
        }
    }

    private Image getImage(Integer key){
        String path = spriteImageFiles.get(key);
        if(path != null){
            return new Image(path);
        }
        else {
            System.out.println("Could not find sprite file for sprite code: " + key.toString());
            //Try to set default
            return new Image(spriteImageFiles.get(1));
        }

    }

    /**
     * The main usage method, returns a Image object corresponding to the Sprite object
     * @param sprite the Sprite object a specific Tile, StaticGameObject etc holds.
     * @return Image, the Image object for the specific Sprite
     */
    public Image getSpriteImage(Sprite sprite){
        if(sprite == null){
            return sprites.get(1);
        }
        return sprites.get(sprite.getSpriteIndex());
    }

}
