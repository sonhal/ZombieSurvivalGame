package engine.controllers.gamestate;

import engine.controllers.interfaces.GameScore;
import engine.controllers.interfaces.Score;

public class PlayerGameScore implements GameScore{

    private int enemiesKilledValue;

    public PlayerGameScore(){

    }

    @Override
    public Score getScore() {
        return calculatePlayerScore();
    }

    public void update(int newEnemyKilledValue){
        enemiesKilledValue += newEnemyKilledValue;
    }

    private Score calculatePlayerScore(){

        if(enemiesKilledValue == 0){
            return new Score() {
                @Override
                public int scoreValue() {
                    return 0;
                }
            };
        }
        return new Score() {
            @Override
            public int scoreValue() {
                return enemiesKilledValue + (int)(System.currentTimeMillis() / 1000);
            }
        };
    }

}
