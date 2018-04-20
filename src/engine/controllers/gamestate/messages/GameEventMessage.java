package engine.controllers.gamestate.messages;

public abstract class GameEventMessage {

    public final Object body;

    public GameEventMessage(Object body){
        this.body = body;
    }

    private enum MessageType{
        ENEMY_KILLED, PLAYER_DEATH, NEW_LEVEL_REACHED, PLAYER_LOW_HEAlTH, NEW_HIGHSCORE_REACHED
    }

}


