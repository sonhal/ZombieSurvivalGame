package engine.entities.components;

import engine.controllers.Direction;
import engine.entities.components.ComponentEvent.*;
import engine.entities.gameobjects.interfaces.IGameObject;
import engine.world.Tile;

import java.util.Optional;

public class AttackComponent extends ScriptableComponent{

    private Direction attackDirection;
    private int damage;

    public AttackComponent(int damage) {
        super(ComponentType.ATTACK_COMPONENT);
        this.damage = damage;
        if (damage < 1){
            throw new IllegalArgumentException("Damage cannot be below 1");
        }
    }

    public void tryAttack( Tile attackTile){
        IGameObject objectToBeAttacked = attackTile.getGameObject();
        if(objectToBeAttacked != null){
            sendMessageToAllComponents(objectToBeAttacked.getComponents(), new HitEvent(damage));
        }
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void update(IGameObject gameObject) {
        if(attackDirection != null){
            Optional<ScriptableComponent> oComponent =
                    getComponentByType(gameObject.getComponents(), ComponentType.TRANSFORM_COMPONENT);
            if(oComponent.isPresent()){
                TransformComponent transformComponent = (TransformComponent) oComponent.get();
                tryAttack(transformComponent.getCurrentTile().getTileInDirection(attackDirection));

                sendMessageToAllComponents(gameObject.getComponents(),new HitEvent(damage));
            }
            attackDirection = null;
        }
    }

    @Override
    public void handle(ComponentEvent event) {
        if(event instanceof AttackEvent){
            attackDirection = ((AttackEvent)event).getAttackDirection();
        }
    }

    @Override
    public void innit(IGameObject gameObject) {
        //Do nothing
    }

    @Override
    public void cleanUp(IGameObject gameObject) {

    }
}
