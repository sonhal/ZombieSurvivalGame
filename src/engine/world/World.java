package engine.world;

import engine.entities.components.ComponentType;
import engine.entities.gameobjects.GameObjectFactory;
import engine.entities.gameobjects.Sprite;
import engine.entities.components.TransformComponent;
import engine.entities.gameobjects.interfaces.IUpdatableGameObject;
import engine.services.ComponentService;

import java.io.Serializable;
import java.util.*;


/**
 * The World class is responsible for generating and acting as a container for the state of the game board.
 * In order to making it posible to store the World object as a file the class implements the Serializable interface.
 */
public class World implements Serializable{

    private List<Tile> world;
    private Tile seed;
    private IUpdatableGameObject player;
    private Random random;

    public World(){ }

    /**
     * This method generates a square form with n number of tiles from the center to each side. Total number generated will be (n*2)²
     * @param n How many rows of tiles is generated on each side of the center.
     */
    public void createNewGameWorld(int n){
        random = new Random();
        world = generate(n);
        seed = findTile(0,0);
        connectTiles(world);
    }

    /**
     * Loads gamestate from save
     * @param tiles List of tiles representing the state of the game.
     * @param player Direct reference to the player object
     */
    public void loadInGameWorld(List<Tile> tiles, IUpdatableGameObject player){
        System.out.println("Connect");
        this.world = tiles;
        seed = findTile(0,0);
        System.out.println("Connected");
        connectTiles(tiles);
        this.player = player;
    }

    /**
     * Generates a unconnected list of gametiles, and assigns each tile cordinates.
     * @param initSize Number of rows to each side of the center
     * @return New list of gametiles.
     */
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

    /**
     * In order to speed up the process of game tiles interacting with each other we connect each gametile to it's neighbour with a refference variable.
     * This makes the process of elements traveling, or checking neighbouring tiles much more cost effective.
     * @param worldToConnect The world object which is going to be connected.
     */
    public void connectTiles(List<Tile> worldToConnect) {

        ConnectLeftRight connectLeftRight = new ConnectLeftRight(worldToConnect);
        ConnectUpDown connectUpDown = new ConnectUpDown(worldToConnect);

        try {
            connectLeftRight.t.join();
            connectUpDown.t.join();
        } catch (InterruptedException e) {

        }

    }

    /**
     * Finds a tile using x and y cordinates
     * This method is mostly only used in unit tests as all game mechanics are using the neighbouring tile system.
     * Method is really costly to run in its current state.
     * @param x X cordinate of the tile
     * @param y Y cordinate of the tile
     * @return Returns tile object
     */
    public Tile findTile(int x, int y){
        return world.stream().filter(tile -> tile.getCordX() == x && tile.getCordY() == y).findFirst().orElse(null);
    }

    /**
     * Object in the center of the map with the cordinates x:0, y:0.
     * Used as a spawning point for the player object.
     * @return Returns a refference to the seed object.
     */
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

    /** Function generating different grass sprites for the tiles in order for the player to feel variation.
     * @return Random generated sprite
     */
    private Sprite generateRandomSprite(){{

        int i = random.nextInt(100);
        if (i < 8){
            return new Sprite(27);
        }
        else {
            return new Sprite(26);
        }}
    }

    /**
     * Function for randomly generating trees on the map.
     * @param spawnTile Tile on which a tree might spawn.
     */
    private void generateWorldObjectOnTile(Tile spawnTile){
        int i = random.nextInt(100);
        if (i < 8){
            GameObjectFactory.createStaticGameObject(new Sprite(28), spawnTile);
        }
    }

    /**
     * Function for getting the list of tiles from the World object.
     * @return List of tiles containing the state of the game world.
     */
    public List<Tile> getWorld() {
        return world;
    }


}
