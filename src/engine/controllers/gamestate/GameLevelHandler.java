package engine.controllers.gamestate;

import engine.controllers.gamestate.messages.GameEventMessage;
import engine.controllers.gamestate.messages.NewLevelMessage;
import engine.controllers.interfaces.*;

import java.util.List;
import java.util.Queue;
import java.util.stream.Stream;

public class GameLevelHandler implements LevelHandler, Messenger {

    private Level currentLevel;
    private Queue<Level> levels;
    private GameStateMessengerMediator gameStateMessengerMediator;

    public GameLevelHandler(GameStateMessengerMediator gameStateMessengerMediator, Queue<Level> levels){
        this.gameStateMessengerMediator = gameStateMessengerMediator;
        this.levels = levels;
        this.currentLevel = levels.poll();
    }

    @Override
    public void update(GameScore score) {
        if(currentLevel.getNextLevelScore() <= score.getScore()){
            currentLevel = levels.poll();
            sendMessage(new NewLevelMessage(currentLevel));
        }
    }

    @Override
    public Level getCurrentLevel() {
        return currentLevel;
    }

    @Override
    public void handleMessage(GameEventMessage message) {
        //Do nothing
    }

    @Override
    public void sendMessage(GameEventMessage message) {
        gameStateMessengerMediator.broadcast(message);
    }
}
