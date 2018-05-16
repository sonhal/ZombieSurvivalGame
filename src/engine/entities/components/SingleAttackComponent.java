package engine.entities.components;

import engine.controllers.Direction;
import engine.entities.components.ComponentEvent.*;
import engine.entities.components.interfaces.AttackComponent;
import engine.entities.gameobjects.interfaces.GameObject;
import engine.world.Tile;

/**
 * AttackComponent triggering a single attack attempt against the Tile in the GameObjects facing direction.
 */
public class SingleAttackComponent extends AttackComponent {

    private Direction attackDirection;
    private final int damage;

    public SingleAttackComponent(int damage) {
        this.damage = damage;
        if (damage < 1){
            throw new IllegalArgumentException("Damage cannot be below 1");
        }
    }

    @Override
    public void tryAttack( Tile attackTile){
        GameObject objectToBeAttacked = attackTile.getGameObject();
        if(objectToBeAttacked != null){
            sendMessageToAllComponents(objectToBeAttacked.getComponents(), new HitEvent(damage));
        }
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public void update(GameObject gameObject) {
        if(attackDirection != null){
                tryAttack(gameObject.getTransformComponent().getCurrentTile().getTileInDirection(attackDirection));
                sendMessageToAllComponents(gameObject.getComponents(),new HitEvent(damage));
        }
        attackDirection = null;
    }

    @Override
    public void handle(ComponentEvent event) {
        if(event instanceof AttackEvent){
            attackDirection = ((AttackEvent)event).getAttackDirection();
        }
    }

    @Override
    public void innit(GameObject gameObject) {
        //Do nothing
    }

    @Override
    public void cleanUp(GameObject gameObject) {

    }
}
