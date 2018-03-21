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
        avatar.heal(100000);
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
        for (int i = 4; i <= 7; i++){
            playerSprites.add(new Sprite(i));
        }
        for (int i = 12; i <= 15; i++){
            playerSprites.add(new Sprite(i));
        }

        Avatar player = AvatarFactory.create(playerSprites);
        player.pickupWeapon(new Gun(this.controller,3,10, 500));
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
            avatar.getTile().deleteGameObject();
            npcController.addToBeDeletedList(this);
            return false;
        }
        else {
            System.out.println(avatar.getHealth());
            return true;}

        }
}



