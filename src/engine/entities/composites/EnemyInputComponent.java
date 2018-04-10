package engine.entities.composites;

import engine.controllers.Direction;
import engine.entities.Avatar;

public class EnemyInputComponent implements InputComponent{

    private Avatar player;
    private TransformComponent transformComponent;
    private Direction moveEvent;
    private Direction attackEvent;

    public EnemyInputComponent(Avatar player, TransformComponent tc){
        transformComponent = tc;
        this.player = player;
    }



    private Direction getDirectionAgainstPlayer(Avatar enemy){

        if (enemy.getCollisionComponent().collided() != null){
            if(enemy.getCollisionComponent().collided() == Direction.UP){
                return Direction.LEFT;
            }
            if(enemy.getCollisionComponent().collided() == Direction.DOWN){
                return Direction.RIGHT;
            }
            if(enemy.getCollisionComponent().collided() == Direction.LEFT){
                return Direction.UP;
            }
            if(enemy.getCollisionComponent().collided() == Direction.RIGHT){
                return Direction.DOWN;
            }
        }
        int playerX = player.getTransformComponent().getCurrentTile().getCordX();
        int playerY = player.getTransformComponent().getCurrentTile().getCordY();
        int enemyX = enemy.getTransformComponent().getCurrentTile().getCordX();
        int enemyY = enemy.getTransformComponent().getCurrentTile().getCordY();

        if (playerX < enemyX){
            if(playerY < enemyY){
                return Direction.DOWN;
            }
            else if (playerY == enemyY){
                return Direction.LEFT;
            }
            else return Direction.UP;
        }
        else {
            if(playerY < enemyY){
                return Direction.DOWN;
            }
            else if (playerY == enemyY){
                return Direction.RIGHT;
            }
            else return Direction.UP;
        }
    }

    private void tryAttack(Avatar enemy){
        if(enemy.getCollisionComponent().collided() != null){
            if(enemy.getTile().getTileInDirection(enemy.getCollisionComponent().collided()).getGameObject() == player){
                attackEvent = enemy.getCollisionComponent().collided();
            }
        }
    }

    @Override
    public void update(Avatar enemy) {
        tryAttack(enemy);
        moveEvent = (getDirectionAgainstPlayer(enemy));
    }

    @Override
    public Direction getMoveEvent() {
        return moveEvent;
    }

    @Override
    public Direction getAttackEvent() {
        return attackEvent;
    }
}
