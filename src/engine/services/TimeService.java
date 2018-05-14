package engine.services;

/**
 * Helper class implementing commonly used time related methods
 */
public class TimeService {

    public static boolean canUpdate(double delay, double lastUpdateTime){
        return (System.currentTimeMillis() > lastUpdateTime  + delay );
    }
}
