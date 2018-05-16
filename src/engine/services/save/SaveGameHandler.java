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


    public static void SaveScore(int score){
        HighScoreData highScore = null;
        try {
            FileInputStream fileIn = new FileInputStream(SAVE_HIGHSCORE_PATH);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            highScore = (HighScoreData) in.readObject();
            in.close();
            fileIn.close();
        }catch (IOException i) {
            System.out.println("No HighScore file found");
        }catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
        if(highScore != null){
            highScore.add(score);
        }
        else {
            highScore = new HighScoreData(10);
            highScore.add(score);
        }
        try {

            FileOutputStream fileOut =
                    new FileOutputStream(SAVE_HIGHSCORE_PATH);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(highScore);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static Optional<HighScoreData> loadHighscore(){
        HighScoreData highScore = null;
        try {
            FileInputStream fileIn = new FileInputStream(SAVE_HIGHSCORE_PATH);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            highScore = (HighScoreData) in.readObject();
            in.close();
            fileIn.close();
        }catch (IOException i) {
            System.out.println("Highscore filen eksisterer ikke");
        }catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
        if(highScore != null){
            return Optional.of(highScore);
        }
        else {
            return Optional.empty();
        }
    }
}
