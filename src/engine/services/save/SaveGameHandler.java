package engine.services.save;
import engine.controllers.GameHandler;
import engine.entities.gameobjects.UpdatableGameObject;
import engine.entities.gameobjects.interfaces.IUpdatableGameObject;
import engine.world.Tile;
import engine.world.World;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveGameHandler {

    private static String SAVE_FILE_PATH = "gamefiles/saves/savefile.ser";
    private static String SAVE_GAME_HANDLER_PATH = "gamefiles/saves/savegamehandlerfile.ser";


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

    public static boolean isSaveFileValid(){
            File saveFile = new File(SAVE_FILE_PATH);
            return saveFile.isFile();
    }


    public static void saveGameHandler(GameHandler gameHandler){
        World worldMap = gameHandler.getWorld();
        List<Tile> world = worldMap.getWorld();
        for (Tile tile:
                world) {
            tile.setUp(null);
            tile.setDown(null);
            tile.setLeft(null);
            tile.setRight(null);
        }
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(SAVE_GAME_HANDLER_PATH);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(gameHandler);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static GameHandler loadGameHandler(){
        GameHandler gameHandler = null;
        try {
            FileInputStream fileIn = new FileInputStream(SAVE_GAME_HANDLER_PATH);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            gameHandler= (GameHandler) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
        return gameHandler;
    }
}
