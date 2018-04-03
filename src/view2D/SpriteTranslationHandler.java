package view2D;

import engine.entities.composites.Sprite;
import javafx.scene.image.Image;


import java.util.HashMap;

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

    public Image getSpriteImage(Sprite sprite){
        return sprites.get(sprite.getSpriteIndex());
    }

}
