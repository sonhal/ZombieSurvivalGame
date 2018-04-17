package engine.entities.composites;

import engine.controllers.Direction;
import engine.entities.interfaces.Hittable;
import engine.entities.interfaces.IGameObject;
import engine.entities.world.Tile;

import java.io.Serializable;
import java.util.Optional;

public class AttackComponent extends ScriptableComponent{

    private Direction attackDirection;
    private int damage;

    public AttackComponent(int damage) {
        super(ComponentType.ATTACK_COMPONENT);
        this.damage = damage;
    }

    public static void tryAttack(int damage, Tile attackTile){
        IGameObject objectToBeAttacked = attackTile.getGameObject();
        if( objectToBeAttacked instanceof Hittable){
            ((Hittable)objectToBeAttacked).hit(damage);
        }
    }

    @Override
    public void update(IGameObject gameObject) {
        if(attackDirection != null){
            Optional<ScriptableComponent> oComponent =
                    getComponentByType(gameObject.getComponents(), ComponentType.TRANSFORM_COMPONENT);
            if(oComponent.isPresent()){
                TransformComponent transformComponent = (TransformComponent) oComponent.get();
                tryAttack(this.damage, transformComponent.getCurrentTile().getTileInDirection(attackDirection));
            }
            attackDirection = null;
        }
    }

    @Override
    public void handle(Message message) {
        if(message.message instanceof Direction){
            attackDirection = (Direction) message.message;
        }
    }

    @Override
    public void innit(IGameObject gameObject) {
        //Do nothing
    }
}
