package engine.controllers.gamestate;

import engine.controllers.gamestate.interfaces.GameScore;

public class PlayerGameScore implements GameScore{

    private int enemiesKilledValue;
    private int score;

    @Override
    public int getScore() {
        return score;
    }

    public void addToScore(int newEnemyKilledValue){
        enemiesKilledValue += newEnemyKilledValue;
        score += newEnemyKilledValue;
    }

}
