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
            case SHOOT_UP: player.shoot(Direction.UP);
                break;
            case SHOOT_DOWN: player.shoot(Direction.DOWN);
                break;
            case SHOOT_LEFT: player.shoot(Direction.LEFT);
                break;
            case SHOOT_RIGHT: player.shoot(Direction.RIGHT);
                break;
        }
    }
}
