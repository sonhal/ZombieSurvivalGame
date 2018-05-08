package engine.gamestate.interfaces;

import java.io.Serializable;

public interface Level extends Serializable {

    int getScoreNeeded();

    int getEnemyPoolValue();

    double getEnemySpeedBaseValue();

    double getHealthPackSpawnRate();

    double getWeaponSpawnRate();

    int getNextLevelScore();

    int getLevel();



}
