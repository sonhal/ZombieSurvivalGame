package engine.entities.world;

import engine.entities.Avatar;
import engine.entities.GameObject;
import engine.entities.composites.Sprite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World implements Serializable{

    private List<Tile> world;
    private Tile seed;
    private Avatar player;
    private Random random;

    public World(int n){
        random = new Random();
        world = generate(n);
        seed = findTile(0,0);
        connectTiles(world);
    }

    protected List<Tile> generate(int initSize){
        List<Tile> newWorld = new ArrayList<Tile>();
        for(int x = -initSize; x < initSize; x++){
            for (int y = -initSize; y < initSize; y++){

                Tile newTile = new Tile(x,y, generateRandomSprite());
                newTile.setGameObject(generateWorldObject());
                newWorld.add(newTile);
            }
        }
        return newWorld;
    }

    public void connectTiles(List<Tile> worldToConnect){
        //Good example of a O(2^n) function, should not be used on large worlds
        worldToConnect.parallelStream().forEach(e->{
                    e.setUp(findTile(e.getCordX(),e.getCordY()+1));
                    e.setDown(findTile(e.getCordX(),e.getCordY()-1));
                    e.setLeft(findTile(e.getCordX()-1,e.getCordY()));
                    e.setRight(findTile(e.getCordX()+1,e.getCordY()));
                }
        );
    }

    public Tile findTile(int x, int y){
        return world.stream().filter(tile -> tile.getCordX() == x && tile.getCordY() == y).findFirst().orElse(null);
    }

    public Tile getSeed() {
        return seed;
    }

    public void setPlayer(Avatar player){
        this.player = player;
        player.getTransformComponent().setCurrentTile(seed);
    }
    public Avatar getPlayer(){
        return player;
    }

    private Sprite generateRandomSprite(){{

        int i = random.nextInt(100);
        if (i < 8){
            return new Sprite(27);
        }
        else {
            return new Sprite(26);
        }}
    }

    private GameObject generateWorldObject(){
        int i = random.nextInt(100);
        if (i < 8){
            return new GameObject(new Sprite(28));
        }
        else {
            return null;
        }
    }

    public List<Tile> getWorld() {
        return world;
    }

    public void setWorld(List<Tile> tiles){
        connectTiles(tiles);
        this.world = tiles;
    }


}
