package engine.controllers.gamestate;

import engine.controllers.interfaces.GameScore;
import engine.controllers.interfaces.Score;

public class PlayerGameScore implements GameScore{

    private int enemiesKilledValue;
    private int score;

    @Override
    public int getScore() {
        return score;
    }

    public void addToScore(int newEnemyKilledValue){
        enemiesKilledValue += newEnemyKilledValue;
        score += enemiesKilledValue;
    }

}
