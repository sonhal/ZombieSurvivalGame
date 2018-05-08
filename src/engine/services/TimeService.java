package engine.services;

public class TimeService {

    public static boolean canUpdate(double delay, double lastUpdateTime){
        return (System.currentTimeMillis() > lastUpdateTime  + delay );
    }
}
