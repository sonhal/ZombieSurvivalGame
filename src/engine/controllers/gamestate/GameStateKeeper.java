package engine.controllers.gamestate;

import engine.controllers.gamestate.messages.EnemyKilledMessage;
import engine.controllers.gamestate.messages.GameEventMessage;
import engine.controllers.interfaces.Messenger;

public class GameStateKeeper implements Messenger{

    private PlayerGameScore playerGameScore;
    private GameLevelHandler gameLevelHandler;
    private GameState gameState;


    public GameStateKeeper(){
        this.playerGameScore = new PlayerGameScore();
        this.gameLevelHandler = new GameLevelHandler();
        this.gameState = new GameState();
    }

    public PlayerGameScore getPlayerGameScore() {
        return playerGameScore;
    }

    public GameLevelHandler getGameLevelHandler() {
        return gameLevelHandler;
    }

    @Override
    public void handleMessage(GameEventMessage message) {
        if(message instanceof EnemyKilledMessage){
            gameState.totalEnemieskilled++;
            playerGameScore.update((int)message.body);
        }
    }

    @Override
    public void sendMessage(GameEventMessage message) {

    }

    private class GameState {
        private int totalEnemieskilled;

    }

}
