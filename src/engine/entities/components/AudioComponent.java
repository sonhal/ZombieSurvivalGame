package engine.entities.components;

import engine.entities.components.ComponentEvent.AttackCompletedEvent;
import engine.entities.components.ComponentEvent.ComponentEvent;
import engine.entities.gameobjects.interfaces.IGameObject;
import engine.services.audio.AudioPlayer;
import engine.services.audio.Sound;

public class AudioComponent extends ScriptableComponent{

    protected boolean attackEvent;
    private int delay;
    private double lastActivationTime;
    private Sound sound;

    public AudioComponent(int delay, Sound sound) {
        super(ComponentType.AUDIO_COMPONENT);
        this.delay = delay;
        this.lastActivationTime = System.currentTimeMillis();
        this.sound = sound;
    }

    @Override
    public void update(IGameObject gameObject) {
        if(attackEvent && canActivate(delay, lastActivationTime)){
            AudioPlayer.getInstance().addToPlayerQueue(sound, 1000);
            lastActivationTime = System.currentTimeMillis();
        }
        attackEvent = false;
    }

    @Override
    public void handle(ComponentEvent event) {
        if(event instanceof AttackCompletedEvent){
            attackEvent = true;
        }
    }

    @Override
    public void innit(IGameObject gameObject) {
    }

    @Override
    public void cleanUp(IGameObject gameObject) {

    }
}
