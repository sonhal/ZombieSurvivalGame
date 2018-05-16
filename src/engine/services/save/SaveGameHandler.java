package engine.services.save;
import engine.controllers.GameHandler;
import engine.entities.gameobjects.UpdatableGameObject;
import engine.entities.gameobjects.interfaces.IUpdatableGameObject;
import engine.gamestate.HighScoreData;
import engine.gamestate.interfaces.HighScore;
import engine.gamestate.interfaces.Score;
import engine.world.Tile;
import engine.world.World;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Service handling the saving of game data to file.
 */
public class SaveGameHandler {

    private static String SAVE_GAME_HANDLER_PATH = "gamefiles/saves/savegamefile.ser";
    private static String SAVE_HIGHSCORE_PATH = "gamefiles/saves/highscore.ser";

    public static boolean isSaveFileValid(){
            File saveFile = new File(SAVE_GAME_HANDLER_PATH);
            return saveFile.isFile();
    }

    private static void saveFile(Object object, String path){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(object);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    private static Optional<Object> loadFile(String path){
        Object object = null;
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            object = in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
        if(object != null){
            return Optional.of(object);
        }
        else {
            return Optional.empty();
        }
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
        saveFile(gameHandler, SAVE_GAME_HANDLER_PATH);
    }

    public static GameHandler loadGameHandler(){
        Optional<Object> oGameHandler =loadFile(SAVE_GAME_HANDLER_PATH);
        return (GameHandler) oGameHandler.orElse(null);
    }


    public static void SaveScore(int score){
        HighScoreData highScoreData;
        Optional<Object> oHighScore = loadFile(SAVE_HIGHSCORE_PATH);

        highScoreData = oHighScore.map(o -> ((HighScoreData) o)).orElseGet(() -> new HighScoreData(10));
        highScoreData.add(score);
        saveFile(highScoreData, SAVE_HIGHSCORE_PATH);
    }

    public static Optional<HighScoreData> loadHighScore(){
        Optional<Object> oHighScore = loadFile(SAVE_HIGHSCORE_PATH);
        return oHighScore.map(o -> (HighScoreData) o);
    }
}
