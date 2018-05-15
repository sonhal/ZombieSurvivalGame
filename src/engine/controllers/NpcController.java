package engine.controllers;

import engine.entities.components.EnemyInputComponent;
import engine.entities.gameobjects.EnemyFactory;
import engine.entities.gameobjects.interfaces.GameObject;
import engine.gamestate.messages.EnemyKilledMessage;
import engine.gamestate.messages.GameEventMessage;
import engine.gamestate.messages.NewLevelMessage;
import engine.controllers.interfaces.Messenger;
import engine.gamestate.interfaces.MessengerMediator;
import engine.entities.gameobjects.BasicEntityBlueprint;
import engine.entities.gameobjects.ZombieBuilder;
import engine.entities.gameobjects.interfaces.IUpdatableGameObject;
import engine.services.pathfinder.PathSearchService;
import engine.world.Tile;
import engine.world.World;
import engine.services.audio.Sound;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class NpcController extends Updater implements Messenger {

    private int spawnInterval = 0;
    private IUpdatableGameObject player;
    private MessengerMediator gameStateMessengerMediator;
    private int gameLevel;
    private GameHandler gameHandler;


    public NpcController(IUpdatableGameObject player, MessengerMediator gameMessengerMediator, GameHandler gameHandler){
        System.out.println("Npc controller created");
        this.player = player;
        this.gameStateMessengerMediator = gameMessengerMediator;
        gameStateMessengerMediator.subscribe(this);
        this.gameHandler = gameHandler;
    }

    public void update(World world){
        ArrayList enemies = getUpdateObjects();
        if (enemies.size() < 100){
            spawner(world);
        }
        getUpdateObjects().forEach(updatable -> {if(updatable.isDead()){
            sendMessage(new EnemyKilledMessage(10));
        }});
        super.update();
        //enemies.stream().forEach((e)->{e.update();});
    }

    protected void spawner(World world){
        Tile spawnTile = locateSpawnTile(world);
        if (spawnTile != null){
            Random rand = new Random();
            System.out.println("Spawning new monster at: X " + spawnTile.getCordX() + " Y " + spawnTile.getCordY());
            IUpdatableGameObject enemy1 = EnemyFactory.createBatEnemy(player, spawnTile, new BasicEntityBlueprint(
                    70 + gameLevel, 20 + gameLevel, (500 - (gameLevel * 50) ) - rand.nextInt(50) , Sound.ZOMBIE_ATTACK), gameHandler);
            addToUpdateList(enemy1);
            spawnInterval = 100;
            IUpdatableGameObject enemy2 = ZombieBuilder.createZombie(player, spawnTile, new BasicEntityBlueprint(
                    30 + gameLevel, 10 + gameLevel, (500 - (gameLevel * 50) ) - rand.nextInt(50) , Sound.ZOMBIE_ATTACK), gameHandler);
            addToUpdateList(enemy2);
            spawnInterval = 10;
        }
    }

    protected Tile locateSpawnTile(World world){
        Tile playerTile = world.getPlayer().getTransformComponent().getCurrentTile();
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

    @Override
    public void handleMessage(GameEventMessage message) {
        if(message instanceof NewLevelMessage){
            gameLevel = ((NewLevelMessage) message).messageBody();
        }
    }

    @Override
    public void sendMessage(GameEventMessage message) {
        if(gameStateMessengerMediator != null){
            gameStateMessengerMediator.broadcast(this, message);
        }
    }

    public void cleanUp(){
        getUpdateObjects().forEach(updatable -> ((GameObject) updatable).getComponentByType(EnemyInputComponent.class).ifPresent(scriptableComponent -> scriptableComponent.cleanUp((GameObject)updatable)));
    }
}
