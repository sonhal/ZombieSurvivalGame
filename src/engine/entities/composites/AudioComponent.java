package engine.entities.composites;

import engine.entities.interfaces.IGameObject;
import engine.services.audio.AudioPlayer;
import engine.services.audio.Sound;
import javafx.application.Platform;

public class AudioComponent extends ScriptableComponent{

    protected AudioPlayer audioPlayer;
    protected boolean attackEvent;

    public AudioComponent() {
        super(ComponentType.AUDIO_COMPONENT);
    }

    @Override
    public void update(IGameObject gameObject) {
        if(attackEvent){
            audioPlayer.addToPlayerQueue(Sound.HIT_1, 1000);
        }
        attackEvent = false;
    }

    @Override
    public void handle(Message message) {
        if(message.event == ComponentEvent.ATTACK_EVENT){
            attackEvent = true;
        }

    }

    @Override
    public void innit(IGameObject gameObject) {
        audioPlayer = AudioPlayer.getInstance();
    }

    @Override
    public void cleanUp(IGameObject gameObject) {

    }
}
