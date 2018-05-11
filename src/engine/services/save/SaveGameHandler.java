package engine.services.save;
import engine.entities.gameobjects.UpdatableGameObject;
import engine.entities.gameobjects.interfaces.IUpdatableGameObject;
import engine.world.Tile;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveGameHandler {

    private static String SAVE_FILE_PATH = "gamefiles/saves/savefile.ser";


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
                    new FileOutputStream(SAVE_FILE_PATH);
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
            FileInputStream fileIn = new FileInputStream(SAVE_FILE_PATH);
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

    public static UpdatableGameObject getPlayerInstance(List<Tile> tiles){
        for (Tile tile:
                tiles) {
            if(tile.getGameObject() instanceof UpdatableGameObject){
                UpdatableGameObject avatar = (UpdatableGameObject) tile.getGameObject();
                if(avatar.isPlayer()){
                    return avatar;
                }
            }
        }
        return null;
    }

    public static ArrayList<IUpdatableGameObject> getEnemyInstances(List<Tile> tiles){
         ArrayList<IUpdatableGameObject> enemies = new ArrayList<>();
        for (Tile tile:
                tiles) {
            if(tile.getGameObject() instanceof UpdatableGameObject){
                UpdatableGameObject avatar = (UpdatableGameObject) tile.getGameObject();
                if(!avatar.isPlayer()){
                    enemies.add(avatar);
                }
            }
        }
        return enemies;
    }
}
