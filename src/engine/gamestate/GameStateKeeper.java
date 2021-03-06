package engine.gamestate;

import engine.gamestate.messages.EnemyKilledMessage;
import engine.gamestate.messages.GameEventMessage;
import engine.controllers.interfaces.Messenger;
import engine.services.save.SaveGameHandler;

import java.io.Serializable;

/**
 * Responsible for keeping the track of relevant statistics in the game.
 * Receives new gameState through the GameStateMessengerMediator and acts accordingly.
 */
public class GameStateKeeper implements Messenger, Serializable {

    private PlayerGameScore playerGameScore;
    private GameLevelHandler gameLevelHandler;
    private GameState gameState;
    private GameStateMessengerMediator messengerMediator;


    public GameStateKeeper(GameStateMessengerMediator messengerMediator){
        this.playerGameScore = new PlayerGameScore();
        this.gameLevelHandler = new GameLevelHandler(messengerMediator, new LevelConfig(100));
        this.gameState = new GameState();
        this.messengerMediator = messengerMediator;
        messengerMediator.subscribe(this);
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
            playerGameScore.addToScore(((EnemyKilledMessage) message).messageBody());
            gameLevelHandler.update(playerGameScore);
        }
    }

    @Override
    public void sendMessage(GameEventMessage message) {

    }

    public void saveScore(){
        SaveGameHandler.SaveScore(playerGameScore.getScore());
    }

    /**
     * Internal helper data class for keeping the data
     */
    private class GameState implements Serializable{
        private int totalEnemieskilled;

    }

}
