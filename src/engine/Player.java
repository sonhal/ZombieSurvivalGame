package engine;

import engine.composites.Sprite;

import java.util.ArrayList;

public class Player extends ScriptableObject implements EventListener {

    private Avatar avatar;
    private GameHandler gameHandler;


    public Player(GameHandler gameHandler) {
        super(gameHandler);
        avatar = createAvatar();
        this.gameHandler = gameHandler;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void update() {
        checkIfAlive();
        avatar.updateIsMoving();
        avatar.updateHitStatus();
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

        Avatar player = AvatarFactory.create(playerSprites,30);
        player.pickupWeapon(new Gun(this.controller,3,10, 50));
        return player;
    }

    @Override
    public void handleEvent(ActionEvent event) {
        switch (event){
            case MOVE_UP: handleMoving(Direction.LEFT);
                break;
            case MOVE_DOWN: handleMoving(Direction.RIGHT);
                break;
            case MOVE_LEFT: handleMoving(Direction.UP);
                break;
            case MOVE_RIGHT: handleMoving(Direction.DOWN);
                break;
            case ATTACK_UP: handleAttacking(Direction.UP);
                break;
            case ATTACK_DOWN: handleAttacking(Direction.DOWN);
                break;
            case ATTACK_LEFT: handleAttacking(Direction.LEFT);
                break;
            case ATTACK_RIGHT: handleAttacking(Direction.RIGHT);
                break;
        }
    }
    private void handleMoving(Direction direction){
        avatar.handleMoving(direction);
    }

    private void handleAttacking(Direction direction){
        avatar.attack(direction);
    }

    public Avatar getAvatar() {
        return avatar;
    }

    private void checkIfAlive(){
        System.out.println(avatar.getHealth());
        if(!avatar.isAlive()){
            gameHandler.playerDied();
        }
    }
}
