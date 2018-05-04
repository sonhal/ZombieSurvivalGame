package engine.controllers;

import engine.entities.ZombieBuilder;
import engine.entities.interfaces.IUpdatableGameObject;
import engine.entities.world.Tile;
import engine.entities.world.World;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class NpcController extends Updater {

    private int spawnInterval = 0;
    private GameHandler gameHandler;
    private IUpdatableGameObject player;


    public NpcController(GameHandler gameHandler, IUpdatableGameObject player){
        System.out.println("Npc controller created");
        this.player = player;
        this.gameHandler = gameHandler;
    }

    public void update(int stage, World world){
        ArrayList enemies = getUpdateObjects();
        if (enemies.size() < 50){
            spawner(world, stage);
        }
        super.update();
        //enemies.stream().forEach((e)->{e.update();});
    }

    protected void spawner(World world, int stage){
        Tile spawnTile = locateSpawnTile(world);
        if (spawnTile != null){
            System.out.println("Spawning new monster at: X " + spawnTile.getCordX() + " Y " + spawnTile.getCordY());
            IUpdatableGameObject enemy = ZombieBuilder.createZombie(player, spawnTile);
            addToUpdateList(enemy);
            spawnInterval = 10;
        }
    }

    protected Tile locateSpawnTile(World world){
        Tile playerTile = world.getPlayer().getTile();
        if(playerTile == null){
            throw new RuntimeException("Player Tile is Null");
        }
        int spawnableRangeX = 20;
        int spawnableRangeY = 20;

            int randomNumX = 0;
            int randomNumY = 0;


                randomNumX = ThreadLocalRandom.current().nextInt(0, spawnableRangeX + 1);
                randomNumY = ThreadLocalRandom.current().nextInt(0, spawnableRangeY + 1);

            randomNumX = (Math.random() < 0.5) ? randomNumX * 1 : randomNumX *-1;
            randomNumY = (Math.random() < 0.5) ? randomNumY * 1 : randomNumY *-1;
            Tile spawnTile = world.findTile(playerTile.getCordX() + randomNumX, playerTile.getCordY() + randomNumY);
            if (spawnTile == null){
                return locateSpawnTile(world);
            }
            if (spawnTile.getGameObject() == null){
                return spawnTile;
            }

        return null;
    }

}
