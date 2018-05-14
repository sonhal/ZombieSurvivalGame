package engine.gamestate;

import java.io.Serializable;

public class LevelConfig implements Serializable {
    private double scoreLevelScalar;

    public LevelConfig( double scoreLevelScalar){
        this.scoreLevelScalar = scoreLevelScalar;
    }

    double getLevelScalar(){
        return scoreLevelScalar;
    }
}
