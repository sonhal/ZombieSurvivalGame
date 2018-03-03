package view2D;

import engine.Sprite;
import javafx.scene.image.Image;


import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;

public class SpriteTranslationHandler {
    private HashMap<Integer, String> spriteImageFiles;


    public SpriteTranslationHandler(){

        spriteImageFiles = new HashMap<>();

        spriteImageFiles.put(1, "view2D/static/grass_sprite.png");
        spriteImageFiles.put(2, "view2D/static/sword.png");
    }

    public Image getImage(Sprite sprite){
        String path = spriteImageFiles.get(sprite.getSpriteIndex());
        if(path != null){
            return new Image(path);
        }
        else {
            System.out.println("Could not find sprite file for sprite code: " + String.valueOf(sprite.getSpriteIndex()));
            return new Image(spriteImageFiles.get(1));
        }

    }

}
