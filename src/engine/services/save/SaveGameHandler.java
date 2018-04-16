package engine.services.save;
import engine.entities.Avatar;
import engine.entities.world.Tile;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveGameHandler {


    public static void saveGame(List<Tile> world){
        for (Tile tile:
                world) {
            tile.setUp(null);
            tile.setDown(null);
            tile.setLeft(null);
            tile.setRight(null);
        }
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("tmp/savefile.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(world);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static List<Tile> loadGame(){
        List<Tile> loadedWorld = null;
        try {
            FileInputStream fileIn = new FileInputStream("tmp/savefile.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            loadedWorld = (List<Tile>)in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
        return loadedWorld;
    }

    public static Avatar getPlayerInstance(List<Tile> tiles){
        for (Tile tile:
                tiles) {
            if(tile.getGameObject() instanceof Avatar){
                Avatar avatar = (Avatar)tile.getGameObject();
                if(avatar.isPlayer()){
                    return avatar;
                }
            }
        }
        return null;
    }

    public static ArrayList<Avatar> getEnemyInstances(List<Tile> tiles){
         ArrayList<Avatar> enemies = new ArrayList<>();
        for (Tile tile:
                tiles) {
            if(tile.getGameObject() instanceof Avatar){
                Avatar avatar = (Avatar)tile.getGameObject();
                if(!avatar.isPlayer()){
                    enemies.add(avatar);
                }
            }
        }
        return enemies;
    }
}