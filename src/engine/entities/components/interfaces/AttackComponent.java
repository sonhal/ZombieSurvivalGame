package engine.entities.components.interfaces;

import engine.entities.components.ScriptableComponent;
import engine.world.Tile;

public abstract class AttackComponent extends ScriptableComponent {

    public abstract void tryAttack(Tile attackTile);

    public abstract int getDamage();
}
