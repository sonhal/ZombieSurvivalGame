package engine.entities.composites;

import engine.entities.interfaces.Hittable;
import engine.entities.interfaces.IGameObject;
import engine.entities.world.Tile;

import java.io.Serializable;

public class AttackComponent implements Serializable{

    public static void tryAttack(int damage, Tile attackTile){
        IGameObject objectToBeAttacked = attackTile.getGameObject();
        if( objectToBeAttacked instanceof Hittable){
            ((Hittable)objectToBeAttacked).hit(damage);
        }
    }
}
