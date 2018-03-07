package engine;

public class EventHandler {
    Avatar player;

    public EventHandler(Avatar player){
        this.player = player;
    }


    public void handle(ActionEvent event){

        switch (event){
            case MOVE_UP: player.move(Direction.UP);
                break;
            case MOVE_DOWN: player.move(Direction.DOWN);
                break;
            case MOVE_LEFT: player.move(Direction.LEFT);
                break;
            case MOVE_RIGHT: player.move(Direction.RIGHT);
                break;
            case ATTACK_UP: player.attack(Direction.UP);
                break;
            case ATTACK_DOWN: player.attack(Direction.DOWN);
                break;
            case ATTACK_LEFT: player.attack(Direction.LEFT);
                break;
            case ATTACK_RIGHT: player.attack(Direction.RIGHT);
                break;
        }
    }
}
