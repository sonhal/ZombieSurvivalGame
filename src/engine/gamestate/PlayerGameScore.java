package engine.gamestate;

import engine.gamestate.interfaces.GameScore;

import java.io.Serializable;

public class PlayerGameScore implements GameScore, Serializable {

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
