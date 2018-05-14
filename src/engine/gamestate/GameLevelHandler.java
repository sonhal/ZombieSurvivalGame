package engine.gamestate;

import engine.gamestate.interfaces.GameScore;
import engine.gamestate.interfaces.LevelHandler;
import engine.gamestate.messages.GameEventMessage;
import engine.gamestate.messages.NewLevelMessage;
import engine.controllers.interfaces.*;

import java.io.Serializable;

public class GameLevelHandler implements LevelHandler, Messenger, Serializable {

    private int currentLevel;
    private LevelConfig config;
    private GameStateMessengerMediator gameStateMessengerMediator;

    public GameLevelHandler(GameStateMessengerMediator gameStateMessengerMediator, LevelConfig config){
        this.gameStateMessengerMediator = gameStateMessengerMediator;
        this.config = config;
        this.currentLevel = 1;
    }

    private void nextLevel(){
        currentLevel++;
        gameStateMessengerMediator.broadcast(this, new NewLevelMessage(currentLevel));
    }

    @Override
    public void update(GameScore score) {
        if((currentLevel * config.getLevelScalar()) < score.getScore()){
            nextLevel();
        }
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    @Override
    public void handleMessage(GameEventMessage message) {
        //Do nothing
    }

    @Override
    public void sendMessage(GameEventMessage message) {
        gameStateMessengerMediator.broadcast(this, message);
    }
}
