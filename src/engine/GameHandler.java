package engine;

import engine.composites.Sprite;
import sun.security.x509.AVA;
import view2D.GameViewController2D;

import java.util.ArrayList;

public class GameHandler{

    World world;
    GameViewController2D gameViewController2D;
    NpcController npcController = new NpcController();
    EventHandler eventHandler;
    Avatar player;
    DrawableMatrix matrix;


    public GameHandler(GameViewController2D gameViewController2D){
    this.gameViewController2D = gameViewController2D;

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
        System.out.println("World state updating");
        npcController.update(1, world);
        System.out.println("updating");
    }

    public DrawableMatrix getDrawableMatrix( int diameter){
        updateWordState();
        return new DrawableMatrix(world, world.getSeed(), diameter, diameter);
    }

    public DrawableTile[][] getDrableWorld(){
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
        player.pickupWeapon(new Weapon(3,1));
        return player;
    }
}
