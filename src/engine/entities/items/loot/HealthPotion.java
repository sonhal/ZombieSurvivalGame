package engine.entities.items.loot;
import engine.entities.gameobjects.Sprite;
import engine.entities.items.Item;

public class HealthPotion extends Item {

    protected int hp;

    public HealthPotion(Sprite sprite, String name, int hp){
        super(sprite, name);
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }
}
