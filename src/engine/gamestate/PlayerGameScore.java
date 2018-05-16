package engine.gamestate;

import engine.gamestate.interfaces.GameScore;

import java.io.Serializable;

/**
 * Keeps track of and contains logic for calculating the Players score during gameplay.
 */
public class PlayerGameScore implements GameScore, Serializable {

    private int score;

    @Override
    public int getScore() {
        return score;
    }

    public void addToScore(int newEnemyKilledValue){
        score += newEnemyKilledValue;
    }

}
