package engine.controllers.gamestate;

import engine.controllers.gamestate.interfaces.Level;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class GameLevelBuilder {

    public static Queue<Level> basicLevels(int levels, int scoreSpace){
        Queue<Level> levelQueue = new ArrayBlockingQueue<Level>(levels);
        for(int i = 0; i < levels; i++){
            levelQueue.add(basicLevel(i, scoreSpace));
        }
        return levelQueue;
    }

    private static Level basicLevel(int level, int scoreSpace){
        if(level == 0){
            return new GameLevel(0,1000,100,
                    10, 10, scoreSpace, level);
        }
        return  new GameLevel(level * scoreSpace, 1000 + (level * 10), 100 + (level * 10),
                10 + (level * 10), 10 + (level * 10), scoreSpace +(scoreSpace * level), level);
    }
}
