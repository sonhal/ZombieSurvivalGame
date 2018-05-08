package engine.gamestate;

import engine.gamestate.interfaces.Level;

public class GameLevel implements Level {

    private int enemyPoolValue;
    private double enemySpeedBaseValue;
    private double healthPackSpawnRate;
    private double weaponSpawnRate;
    public final int scoreNeeded;
    public final int nextLevelScore;
    private final int level;

    public GameLevel(int scoreNeeded, int enemyPoolValue, double enemySpeedBaseValue, double healthPackSpawnRate, double weaponSpawnRate, int nextLevelScore, int level){
        this.enemyPoolValue = enemyPoolValue;
        this.enemySpeedBaseValue = enemySpeedBaseValue;
        this.healthPackSpawnRate = healthPackSpawnRate;
        this.weaponSpawnRate = weaponSpawnRate;
        this.scoreNeeded = scoreNeeded;
        this.nextLevelScore = nextLevelScore;
        this.level = level;
    }


    @Override
    public int getScoreNeeded() {
        return scoreNeeded;
    }

    @Override
    public int getEnemyPoolValue() {
        return enemyPoolValue;
    }

    @Override
    public double getEnemySpeedBaseValue() {
        return enemySpeedBaseValue;
    }

    @Override
    public double getHealthPackSpawnRate() {
        return healthPackSpawnRate;
    }

    @Override
    public double getWeaponSpawnRate() {
        return weaponSpawnRate;
    }

    @Override
    public int getNextLevelScore() {
        return nextLevelScore;
    }

    @Override
    public int getLevel() {
        return level;
    }
}
