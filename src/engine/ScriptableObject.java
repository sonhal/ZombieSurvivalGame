package engine;

public abstract class ScriptableObject {

    protected GameHandler gameHandler;


    public ScriptableObject(GameHandler gameHandler){
        this.gameHandler = gameHandler;
    }

    public abstract void start();
    public abstract void stop();
    public abstract void update();
}
