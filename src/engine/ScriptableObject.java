package engine;

public abstract class ScriptableObject {

    protected ScriptableObjectController controller;


    public ScriptableObject(ScriptableObjectController controller){
        this.controller = controller;
    }

    public abstract void start();
    public abstract void stop();
    public abstract void update();
}
