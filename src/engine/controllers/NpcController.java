package engine.controllers;

import engine.entities.Avatar;
import engine.entities.Enemy;
import engine.entities.GameObject;
import engine.entities.composites.Sprite;
import engine.entities.world.Tile;
import engine.entities.world.World;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class NpcController extends ScriptableObjectController {

    int spawnInterval = 0;
    GameHandler gameHandler;
    Avatar player;


    public NpcController(GameHandler gameHandler, Avatar player){
        System.out.println("Npc controller created");
        this.player = player;
        this.gameHandler = gameHandler;
    }

    public void update(int stage, World world){
        ArrayList enemies = getScriptableObjects();
        System.out.println(enemies.size());
        if (enemies.size() < 50){
            spawner(world, stage);
        }
        updateScriptableObjects();
        //enemies.stream().forEach((e)->{e.update();});
    }

    protected void spawner(World world, int stage){
        Tile spawnTile = locateSpawnTile(world);
        if (spawnTile != null){
            System.out.println("Spawning new monster at: X " + spawnTile.getCordX() + " Y " + spawnTile.getCordY());
           Enemy enemy = new Enemy(this, gameHandler, player);
           enemy.getAvatar().getTransformComponent().setCurrentTile(spawnTile);
           addToUpdateList(enemy);
           spawnInterval= 10;
        }
    }

    protected Tile locateSpawnTile(World world){
        Tile playerTile = world.getPlayer().getTile();
        int spawnableRangeX = 20;
        int spawnableRangeY = 20;
            System.out.println("Locating spawnTile");

            int randomNumX = 0;
            int randomNumY = 0;


                randomNumX = ThreadLocalRandom.current().nextInt(0, spawnableRangeX + 1);
                randomNumY = ThreadLocalRandom.current().nextInt(0, spawnableRangeY + 1);

            randomNumX = (Math.random() < 0.5) ? randomNumX * 1 : randomNumX *-1;
            randomNumY = (Math.random() < 0.5) ? randomNumY * 1 : randomNumY *-1;
            Tile spawnTile = world.findTile(playerTile.getCordX() + randomNumX, playerTile.getCordY() + randomNumY);
            if (spawnTile.getGameObject() == null){
                return spawnTile;
            }

        return null;
    }

    protected GameObject spawnEnemy(Tile tile, int stage){
        // TODO-me Add logic which semi-randomly choses a enemy to spawn depending on which stage the player have reached here.
        GameObject enemy = new GameObject(new Sprite(2));
        return enemy;
    }

}
