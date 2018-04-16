package engine.entities.composites;

import engine.entities.interfaces.Hittable;
import engine.entities.interfaces.IGameObject;
import engine.entities.world.Tile;

import java.io.Serializable;

public class AttackComponent implements ScriptableComponent{

    public static void tryAttack(int damage, Tile attackTile){
        IGameObject objectToBeAttacked = attackTile.getGameObject();
        if( objectToBeAttacked instanceof Hittable){
            ((Hittable)objectToBeAttacked).hit(damage);
        }
    }

    @Override
    public void update(IGameObject gameObject) {

    }

    @Override
    public void handle(Message message) {

    }

    @Override
    public void innit(IGameObject gameObject) {

    }
}
