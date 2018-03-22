package engine;

import engine.composites.GraphicsComponent;
import engine.composites.Sprite;
import engine.composites.TransformComponent;

import java.util.ArrayList;

public class Enemy extends ScriptableObject {
    private Avatar avatar;
    private double MOVE_DELAY = 1000; //millis
    private Avatar player;
    private ScriptableObjectController npcController;



    public Enemy(ScriptableObjectController npcController, ScriptableObjectController gameHandler, Avatar player) {
        super(gameHandler);
        this.npcController = npcController;
        avatar = createAvatar();
        this.player = player;
        avatar.setMoveDelay(MOVE_DELAY);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void update() {
        if(checkIfAlive()){
            tryAttack();
            avatar.handleMoving(getDirectionAgainstPlayer());
            if(avatar.isHit()){avatar.updateHitStatus();}
            avatar.updateHitStatus();
            avatar.updateIsMoving();
        }
    }

    /**
     * Creates creates the sprites and sets them on a new Avatar Object
     * @return Avatar
     */
    private Avatar createAvatar(){
        ArrayList<Sprite> playerSprites = new ArrayList<>();

        /*'
        Setting the sprites for a enemy, the numbering is not intuitive
        */
        playerSprites.add(new Sprite(18)); //down
        playerSprites.add(new Sprite(19)); //up
        playerSprites.add(new Sprite(20)); //left
        playerSprites.add(new Sprite(21)); //right



        playerSprites.add(new Sprite(23)); //up
        playerSprites.add(new Sprite(22)); //down
        playerSprites.add(new Sprite(24)); //right
        playerSprites.add(new Sprite(25)); //left





        Avatar player = AvatarFactory.create(playerSprites, 10);
        player.pickupWeapon(new MeeleWeapon(1,5,1000));
        return player;
    }


    private void handleAttacking(Direction direction){
        this.avatar.attack(direction);
    }


    public Avatar getAvatar() {
        return avatar;
    }

    private Direction getDirectionAgainstPlayer(){
        int playerX = player.getTransformComponent().getCurrentTile().cordX;
        int playerY = player.getTransformComponent().getCurrentTile().cordY;
        int enemyX = avatar.getTransformComponent().getCurrentTile().cordX;
        int enemyY = avatar.getTransformComponent().getCurrentTile().cordY;

        if (playerX < enemyX){
            if(playerY < enemyY){
                return Direction.DOWN;
            }
            else if (playerY == enemyY){
                return Direction.LEFT;
            }
            else return Direction.UP;
        }
        else {
            if(playerY < enemyY){
                return Direction.DOWN;
            }
            else if (playerY == enemyY){
                return Direction.RIGHT;
            }
            else return Direction.UP;
        }
    }

    private boolean checkIfAlive(){
        if(!avatar.isAlive()){
            System.out.println("Enemy died!");
            avatar.getTile().deleteGameObject();
            npcController.addToBeDeletedList(this);
            return false;
        }
        else {
            return true;}
        }

    private boolean isPlayerInRange(Tile tile){
        return  (tile.getGameObject() == player);
    }

    public void tryAttack(){
        if(avatar.getCollisionComponent().collided() != null){
           if(avatar.getTile().getTileInDirection(avatar.getCollisionComponent().collided()).getGameObject() == player){
               avatar.attack(avatar.getCollisionComponent().collided());
           }
        }
    }
}



