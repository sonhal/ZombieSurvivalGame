package engine.controllers.interfaces;

import java.io.Serializable;

public interface Level extends Serializable {

    int getEnemyPoolValue();

    double getEnemySpeedBaseValue();

    double getHealthPackSpawnRate();

    double getWeaponSpawnRate();

}
