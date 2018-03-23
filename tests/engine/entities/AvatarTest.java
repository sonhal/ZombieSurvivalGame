package engine.entities;


import engine.controllers.Direction;
import engine.entities.composites.*;
import engine.entities.items.weapons.MeleeWeapon;
import engine.entities.items.weapons.Weapon;
import engine.entities.world.Tile;
import engine.entities.world.World;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AvatarTest {

    private Avatar avatar;
    private TransformComponent transformComponent;
    private GraphicsComponent graphicsComponent;
    private HealthComponent healthComponent;
    private AttackComponent attackComponent;
    private Sprite sprite;
    private Weapon weapon;
    private CollisionComponent collisionComponent;
    private World world;

    @BeforeEach
    void setUp() {
        sprite = new Sprite(2);
        graphicsComponent = new GraphicsComponent(sprite);
        healthComponent = new HealthComponent(10);
        attackComponent = new AttackComponent();
        collisionComponent = new CollisionComponent();

        avatar = new Avatar(graphicsComponent,healthComponent,attackComponent,collisionComponent);
        world = new World(10);
        world.setPlayer(avatar);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void attack() {
        attackComponent.setWeapon(new MeleeWeapon(1,1,1));
        GameObject testObject = new GameObject(new GraphicsComponent(new Sprite(1)));
        Tile attackTile = avatar.getTile().getUp();
        assertEquals(attackTile.getDown().getGameObject(), avatar, "Something went wrong setting the attacked Object");
        attackTile.setGameObject(testObject);
        transformComponent.setFacingDirection(Direction.UP);
        avatar.attack(Direction.UP);
        assertTrue(testObject.isHit(), "GameObject was not hit");
    }


    @Test
    void getSpriteIDByDirection() {
        assertEquals(avatar.getSpriteIDByDirection(Direction.UP), 3, "Up direction up should be 3, is: " + avatar.getSpriteIDByDirection(Direction.UP));
        assertEquals(avatar.getSpriteIDByDirection(Direction.DOWN), 4, "Down direction up should be 4, is: " + avatar.getSpriteIDByDirection(Direction.UP));
        assertEquals(avatar.getSpriteIDByDirection(Direction.LEFT), 2, "Left direction up should be 2, is: " + avatar.getSpriteIDByDirection(Direction.UP));
        assertEquals(avatar.getSpriteIDByDirection(Direction.RIGHT), 1, "Right direction up should be 3, is: " + avatar.getSpriteIDByDirection(Direction.UP));
    }

    @Test
    void getSpriteMovingIDByDirection() {
        assertEquals(avatar.getSpriteMovingIDByDirection(Direction.UP), 7, "Up direction up should be 7, is: " + avatar.getSpriteIDByDirection(Direction.UP));
        assertEquals(avatar.getSpriteMovingIDByDirection(Direction.DOWN), 8, "Down direction up should be 8, is: " + avatar.getSpriteIDByDirection(Direction.UP));
        assertEquals(avatar.getSpriteMovingIDByDirection(Direction.LEFT), 5, "Left direction up should be 5, is: " + avatar.getSpriteIDByDirection(Direction.UP));
        assertEquals(avatar.getSpriteMovingIDByDirection(Direction.RIGHT), 6, "Right direction up should be 6, is: " + avatar.getSpriteIDByDirection(Direction.UP));
    }

    @Test
    void handleMoving() {
    }

    @Test
    void setMoveDelay() {
    }

    @Test
    void updateIsMoving() {
    }

    @Test
    void getCollisionComponent() {
        assertEquals(avatar.getCollisionComponent(), collisionComponent);
    }


    @Test
    void consumeHealthPack() {
    }

    @Test
    void pickupWeapon() {
    }

}