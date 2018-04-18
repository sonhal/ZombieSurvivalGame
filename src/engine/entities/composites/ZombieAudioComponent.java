package engine.entities.composites;

import engine.entities.interfaces.IGameObject;
import engine.services.audio.AudioPlayer;
import engine.services.audio.Sound;
import javafx.application.Platform;

public class ZombieAudioComponent extends AudioComponent{


    @Override
    public void update(IGameObject gameObject){
        if(attackEvent){
            AudioPlayer.getInstance().addToPlayerQueue(Sound.ZOMBIE_ATTACK, 1000);
        }
        attackEvent = false;
    }
}
