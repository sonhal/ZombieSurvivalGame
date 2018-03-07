package view2D;

import engine.composites.Sprite;
import javafx.scene.image.Image;


import java.util.HashMap;

public class SpriteTranslationHandler {
    private HashMap<Integer, String> spriteImageFiles;
    private HashMap<Integer, Image> sprites;


    public SpriteTranslationHandler(){

        spriteImageFiles = new HashMap<>();
        sprites = new HashMap<>();

        spriteImageFiles.put(1, "view2D/static/grass_sprite.png");
        spriteImageFiles.put(2, "view2D/static/sword.png");
        spriteImageFiles.put(3, "view2D/static/water_bucket.png");

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
