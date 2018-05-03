package engine.entities.composites;

import engine.entities.interfaces.IGameObject;
import engine.services.audio.AudioPlayer;
import engine.services.audio.Sound;
import javafx.application.Platform;

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
    public void handle(Message message) {
        if(message.event == ComponentEvent.ATTACK_COMPLETED_EVENT){
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
