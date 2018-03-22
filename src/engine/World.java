package engine;

import java.util.ArrayList;
import java.util.List;

public class World {
    private List<Tile> world;

    //Placeholder for avatar
    private Tile seed;
    private Avatar player;

    public World(int n){
        world = generate(n);
        seed = findTile(0,0);

        connectTiles(world);
    }

    protected List<Tile> generate(int initSize){
        List<Tile> newWorld = new ArrayList<Tile>();
        for(int x = -initSize; x < initSize; x++){
            for (int y = -initSize; y < initSize; y++){
                newWorld.add(new Tile(x,y));
            }
        }
        return newWorld;
    }

    public void connectTiles(List<Tile> worldToConnect){
        //Good example of a O(2^n) function, should not be used on large worlds
        worldToConnect.parallelStream().forEach(e->{
                    e.setUp(findTile(e.cordX,e.cordY+1));
                    e.setDown(findTile(e.cordX,e.cordY-1));
                    e.setLeft(findTile(e.cordX-1,e.cordY));
                    e.setRight(findTile(e.cordX+1,e.cordY));
                }
        );
    }

    public Tile findTile(int x, int y){
        return world.stream().filter(tile -> tile.cordX == x && tile.cordY == y).findFirst().orElse(null);
    }

    public Tile getSeed() {
        return seed;
    }

    public void setPlayer(Avatar player){
        this.player = player;
        seed.setGameObject(player);
    }
    public Avatar getPlayer(){
        return player;
    }



}
