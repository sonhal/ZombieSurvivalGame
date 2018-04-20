package engine.controllers.gamestate;

import engine.controllers.interfaces.Level;

public class GameLevel implements Level {

    private int enemyPoolValue;
    private double enemySpeedBaseValue;
    private double healthPackSpawnRate;
    private double weaponSpawnRate;

    public GameLevel(int enemyPoolValue, double enemySpeedBaseValue, double healthPackSpawnRate, double weaponSpawnRate){
        this.enemyPoolValue = enemyPoolValue;
        this.enemySpeedBaseValue = enemySpeedBaseValue;
        this.healthPackSpawnRate = healthPackSpawnRate;
        this.weaponSpawnRate = weaponSpawnRate;
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
}
