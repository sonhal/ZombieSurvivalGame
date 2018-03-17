package engine;

public abstract class Shootable extends GameObject{

    private Direction direction;
    private int damage;
    private int range;

    public Shootable(Sprite sprite, int health, int damage, int range) {
        super(sprite, health);
        this.damage = damage;
        this.range = range;
    }

    public int getDamage(){
        return damage;
    }




}
