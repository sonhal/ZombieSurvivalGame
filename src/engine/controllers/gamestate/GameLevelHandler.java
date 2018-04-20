package engine.controllers.gamestate;

import engine.controllers.interfaces.Level;
import engine.controllers.interfaces.LevelHandler;
import engine.controllers.interfaces.Score;

public class GameLevelHandler implements LevelHandler {

    private Score currentScore;

    public GameLevelHandler(){
        this.currentScore = ((Score) () -> 0);
    }

    @Override
    public void update(Score score) {
        currentScore = score;
    }

    @Override
    public Level getCurrentLevel() {
        return getLevelOnScore(currentScore);
    }


    private Level getLevelOnScore(Score score){

        if(score.scoreValue() > 1000){
            return new GameLevel(1000,100,10,10);
        }
        else{
            return new GameLevel(500,500,50,50);
        }
    }
}
