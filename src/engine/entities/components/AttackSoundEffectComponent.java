package engine.entities.components;

import engine.entities.components.ComponentEvent.AttackCompletedEvent;
import engine.entities.components.ComponentEvent.ComponentEvent;
import engine.entities.components.ComponentEvent.HitEvent;
import engine.entities.components.ComponentEvent.PickUpWeaponEvent;
import engine.entities.components.interfaces.AudioComponent;
import engine.entities.gameobjects.interfaces.GameObject;
import engine.services.audio.AudioPlayer;
import engine.services.audio.Sound;

/**
 * Plays a Sound in the event of a AttackCompleted Event.
 */
public class AttackSoundEffectComponent extends AudioComponent {

    protected boolean attackEvent;
    private int delay;
    private double lastActivationTime;
    private Sound sound;

    public AttackSoundEffectComponent(int delay, Sound sound) {
        this.delay = delay;
        this.lastActivationTime = System.currentTimeMillis();
        this.sound = sound;
    }

    @Override
    public void update(GameObject gameObject) {
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
    public void innit(GameObject gameObject) {
    }

    @Override
    public void cleanUp(GameObject gameObject) {

    }
}
