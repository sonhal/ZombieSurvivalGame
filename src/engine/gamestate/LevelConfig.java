package engine.gamestate;

public class LevelConfig {
    private double scoreLevelScalar;

    public LevelConfig( double scoreLevelScalar){
        this.scoreLevelScalar = scoreLevelScalar;
    }

    double getLevelScalar(){
        return scoreLevelScalar;
    }
}
