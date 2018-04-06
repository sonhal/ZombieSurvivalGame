package engine.entities.composites;

import engine.entities.GameObject;
import engine.entities.Hittable;
import engine.entities.world.Tile;

public class AttackComponent {

    public static void tryAttack(int damage, Tile attackTile){
        GameObject objectToBeAttacked = attackTile.getGameObject();
        if( objectToBeAttacked instanceof Hittable){
            ((Hittable)objectToBeAttacked).hit(damage);
        }
    }
}
