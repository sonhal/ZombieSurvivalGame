package engine.entities.components;

import engine.entities.components.ComponentEvent.ComponentEvent;
import engine.entities.components.ComponentEvent.PickUpWeaponEvent;
import engine.entities.components.ComponentEvent.PickupHealthEvent;
import engine.entities.components.interfaces.AudioComponent;
import engine.entities.gameobjects.interfaces.GameObject;
import engine.services.audio.AudioPlayer;
import engine.services.audio.Sound;

public class PickupItemSoundEffectComponent extends AudioComponent {

    protected boolean pickupEvent;
    private boolean pickedUpHealth;
    private int delay;
    private double lastActivationTime;
    private Sound weaponPickupsound;
    private Sound healthPickupSound;

    public PickupItemSoundEffectComponent(int delay, Sound weaponPickupSound, Sound healthPickupSound) {
        this.delay = delay;
        this.weaponPickupsound = weaponPickupSound;
        this.healthPickupSound = healthPickupSound;
    }

    @Override
    public void update(GameObject gameObject) {
        if(pickupEvent && canActivate(delay, lastActivationTime)){
            AudioPlayer.getInstance().addToPlayerQueue(weaponPickupsound, 1000);
            lastActivationTime = System.currentTimeMillis();
        }
        if(pickedUpHealth && canActivate(delay, lastActivationTime)){
            AudioPlayer.getInstance().addToPlayerQueue(healthPickupSound, 1000);
            lastActivationTime = System.currentTimeMillis();
        }
        pickedUpHealth = false;
        pickupEvent= false;
    }

    @Override
    public void handle(ComponentEvent event) {
        if(event instanceof PickUpWeaponEvent){
            pickupEvent = true;
        }
        if(event instanceof PickupHealthEvent){
            pickedUpHealth = true;
        }
    }

    @Override
    public void innit(GameObject gameObject) {

    }

    @Override
    public void cleanUp(GameObject gameObject) {

    }
}
