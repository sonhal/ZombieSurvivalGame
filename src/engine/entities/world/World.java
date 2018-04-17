package engine.entities.world;

import engine.entities.*;
import engine.entities.composites.AvatarTransformComponent;
import engine.entities.composites.ComponentType;
import engine.entities.composites.Sprite;
import engine.entities.composites.TransformComponent;
import engine.entities.interfaces.IGameObject;
import engine.entities.interfaces.IUpdatableGameObject;
import engine.services.ComponentService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World implements Serializable{

    private List<Tile> world;
    private Tile seed;
    private IUpdatableGameObject player;
    private Random random;

    public World(){ }

    public void createNewGameWorld(int n){
        random = new Random();
        world = generate(n);
        seed = findTile(0,0);
        connectTiles(world);
    }

    public void loadInGameWorld(List<Tile> tiles, IUpdatableGameObject player){
        System.out.println("Connect");
        this.world = tiles;
        seed = findTile(0,0);
        System.out.println("Connected");
        connectTiles(tiles);


        this.player = player;
    }

    protected List<Tile> generate(int initSize){
        List<Tile> newWorld = new ArrayList<Tile>();
        for(int x = -initSize; x < initSize; x++){
            for (int y = -initSize; y < initSize; y++){

                Tile newTile = new Tile(x,y, generateRandomSprite());
                generateWorldObjectOnTile(newTile);
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
        });
    }

    public Tile findTile(int x, int y){
        return world.stream().filter(tile -> tile.getCordX() == x && tile.getCordY() == y).findFirst().orElse(null);
    }

    public Tile getSeed() {
        return seed;
    }

    public void setPlayer(IUpdatableGameObject player){
        this.player = player;
        if(ComponentService.getComponentByType(player.getComponents(), ComponentType.TRANSFORM_COMPONENT).isPresent()){
            TransformComponent at = (TransformComponent)
                    ComponentService.getComponentByType(player.getComponents(), ComponentType.TRANSFORM_COMPONENT).get();
            at.setCurrentTile(seed);
        }
    }

    public IUpdatableGameObject getPlayer(){
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

    private void generateWorldObjectOnTile(Tile startTile){
        int i = random.nextInt(100);
        if (i < 8){
            GameObjectFactory.createStaticGameObject(new Sprite(28), startTile);
        }
    }

    public List<Tile> getWorld() {
        return world;
    }


}
