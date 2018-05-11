package engine.entities.gameobjects;

import engine.services.audio.Sound;

public class BasicEntityBlueprint {

    public int health;
    public int attackDamage;
    public int moveDelay;
    public Sound attackSound;

    public BasicEntityBlueprint(int health, int attackDamage, int moveDelay, Sound attackSound){
        this.health = health;
        this.attackDamage = attackDamage;
        this.moveDelay = moveDelay;
        this.attackSound = attackSound;
    }
}
