package engine.services.save;

import engine.entities.world.Tile;
import engine.entities.world.World;

import java.io.*;
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
}
