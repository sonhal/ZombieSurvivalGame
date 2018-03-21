package engine;

import engine.composites.Sprite;
import view2D.GameViewController2D;

import java.util.ArrayList;

public class GameHandler{

    World world;
    GameViewController2D gameViewController2D;
    EventHandler eventHandler;
    Avatar player;
    DrawableMatrix matrix;
    ArrayList<ScriptableObject> scriptableObjects;
    ArrayList<ScriptableObject> scriptableToBeDeleted;


    public GameHandler(GameViewController2D gameViewController2D){
    this.gameViewController2D = gameViewController2D;
    this.scriptableObjects = new ArrayList<>();
    this.scriptableToBeDeleted = new ArrayList<>();

    this.player = createPlayer();

    createWorld(50);
    world.setPlayer(player);
    this.eventHandler = new EventHandler(player);
    this.matrix = getDrawableMatrix(10);
    }

    public void createWorld() {
        this.world = new World(10);
    }

    public void createWorld(int n) {
        this.world = new World(n);
    }

    public void updateWordState(){
        updateScritableObjects();
    }

    public DrawableMatrix getDrawableMatrix( int diameter){
        //updateWordState();
        return new DrawableMatrix(world, world.getSeed(), diameter, diameter);
    }

    public DrawableTile[][] getDrawableWorld(){
        updateWordState();
        return matrix.generateDrawable(world,player.getTransformComponent().getCurrentTile(),10,10);
    }

    public void sendEvent(ActionEvent event){
        eventHandler.handle(event);
    }

    public void setObjectInWorld(){
        int x = player.getTransformComponent().getCurrentTile().cordX;
        int y = player.getTransformComponent().getCurrentTile().cordY;
        Tile tile = world.findTile(x + 3,y + 3);
        tile.setGameObject(GameObjectFactory.create(new Sprite(3)));
    }

    /**
     * Creates new player with correct Sprites for animation added.
     * @return Avatar
     */
    private Avatar createPlayer(){
        ArrayList<Sprite> playerSprites = new ArrayList<>();
        for (int i = 4; i <= 7; i++){
            playerSprites.add(new Sprite(i));
        }

        Avatar player = AvatarFactory.create(playerSprites);
        player.pickupWeapon(new Gun(this,3,1));
        return player;
    }

    private void updateScritableObjects(){
        scriptableObjects.removeAll(scriptableToBeDeleted);

        for (ScriptableObject scriptableObject:
             scriptableObjects) {
            scriptableObject.update();
        }
    }

    public void addToUpdateList(ScriptableObject scriptableObject){
        this.scriptableObjects.add(scriptableObject);
    }

    public void addToBeDeletedList(ScriptableObject scriptableObject){
        scriptableToBeDeleted.add(scriptableObject);
    }
}
