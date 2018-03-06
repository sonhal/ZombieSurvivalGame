package engine;

public class EventHandler {
    Avatar player;

    public EventHandler(Avatar player){
        this.player = player;
    }


    public void handle(ActionEvent event){

        switch (event){
            case MOVE_UP: player.move(Direction.UP);
            case MOVE_DOWN: player.move(Direction.DOWN);
            case MOVE_LEFT: player.move(Direction.LEFT);
            case MOVE_RIGHT: player.move(Direction.RIGHT);
            case SHOOT_UP: player.shoot(Direction.UP);
            case SHOOT_DOWN: player.shoot(Direction.DOWN);
            case SHOOT_LEFT: player.shoot(Direction.LEFT);
            case SHOOT_RIGHT: player.shoot(Direction.RIGHT);
        }
    }
}
