package engine.entities;

import engine.entities.items.weapons.MeleeWeapon;
import engine.controllers.Direction;
import engine.entities.composites.Sprite;
import engine.controllers.ScriptableObjectUpdater;
import engine.entities.world.Tile;

import java.util.ArrayList;


/**
 * Responsible for a single enemy in the game world.
 * Instantiates and handles the Avatar representing the Enemy in the World.
 */
public class Enemy extends ScriptableObject {
    private Avatar avatar;
    private double MOVE_DELAY = 1000; //millis
    private Avatar player;

    public Enemy(Avatar player) {
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
        player.pickupWeapon(new MeleeWeapon(1,5,1000));
        return player;
    }


    private void handleAttacking(Direction direction){
        this.avatar.attack(direction);
    }


    public Avatar getAvatar() {
        return avatar;
    }

    private Direction getDirectionAgainstPlayer(){

        if (avatar.getCollisionComponent().collided() != null){
            if(avatar.getCollisionComponent().collided() == Direction.UP){
                return Direction.LEFT;
            }
            if(avatar.getCollisionComponent().collided() == Direction.DOWN){
                return Direction.RIGHT;
            }
            if(avatar.getCollisionComponent().collided() == Direction.LEFT){
                return Direction.UP;
            }
            if(avatar.getCollisionComponent().collided() == Direction.RIGHT){
                return Direction.DOWN;
            }
        }
        int playerX = player.getTransformComponent().getCurrentTile().getCordX();
        int playerY = player.getTransformComponent().getCurrentTile().getCordY();
        int enemyX = avatar.getTransformComponent().getCurrentTile().getCordX();
        int enemyY = avatar.getTransformComponent().getCurrentTile().getCordY();

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
            avatar.getTile().clearGameObject();
            die();
            return false;
        }
        else {
            return true;}
        }

    private boolean isPlayerInRange(Tile tile){
        return  (tile.getGameObject() == player);
    }

    private void tryAttack(){
        if(avatar.getCollisionComponent().collided() != null){
           if(avatar.getTile().getTileInDirection(avatar.getCollisionComponent().collided()).getGameObject() == player){
               handleAttacking(avatar.getCollisionComponent().collided());
           }
        }
    }
}



