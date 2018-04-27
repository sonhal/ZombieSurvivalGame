package engine.controllers;

import engine.controllers.gamestate.GameStateMessengerMediator;
import engine.controllers.gamestate.messages.GameEventMessage;
import engine.controllers.gamestate.messages.NewLevelMessage;
import engine.controllers.interfaces.Messenger;
import engine.controllers.interfaces.MessengerMediator;
import engine.entities.ZombieBuilder;
import engine.entities.interfaces.IUpdatableGameObject;
import engine.entities.world.Tile;
import engine.entities.world.World;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class NpcController extends Updater implements Messenger {

    private int spawnInterval = 0;
    private IUpdatableGameObject player;
    private MessengerMediator gameStateMessengerMediator;


    public NpcController(IUpdatableGameObject player, MessengerMediator gameMessengerMediator){
        System.out.println("Npc controller created");
        this.player = player;
        this.gameStateMessengerMediator = gameMessengerMediator;
        this.gameStateMessengerMediator.subscribe(this);
    }

    public void update(World world){
        ArrayList enemies = getUpdateObjects();
        if (enemies.size() < 50){
            spawner(world);
        }
        super.update();
        //enemies.stream().forEach((e)->{e.update();});
    }

    protected void spawner(World world){
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
            System.out.println("Locating spawnTile");

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

    @Override
    public void handleMessage(GameEventMessage message) {
        if(message instanceof NewLevelMessage){
            //[TODO]
        }
    }

    @Override
    public void sendMessage(GameEventMessage message) {
        if(gameStateMessengerMediator != null){
            gameStateMessengerMediator.broadcast(message);
        }
    }
}
