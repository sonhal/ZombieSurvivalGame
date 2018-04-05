package engine.entities.composites;

public class TimeComponent {

    public static boolean canUpdate(double delay, double lastUpdateTime){
        return (System.currentTimeMillis() > lastUpdateTime  + delay );
    }
}
