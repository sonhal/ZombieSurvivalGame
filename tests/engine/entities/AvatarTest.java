package engine.entities;


import engine.controllers.Direction;
import engine.entities.composites.*;
import engine.entities.interfaces.Hittable;
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
    private WeaponComponent weaponComponent;
    private Sprite sprite;
    private Weapon weapon;
    private CollisionComponent collisionComponent;
    private World world;

    @BeforeEach
    void setUp() {
        sprite = new Sprite(2);
        graphicsComponent = new GraphicsComponent(sprite);
        healthComponent = new HealthComponent(10);
        weaponComponent = new WeaponComponent();
        collisionComponent = new CollisionComponent();

        //avatar = new Avatar(graphicsComponent,healthComponent, weaponComponent,collisionComponent);
        world = new World(10);
        world.setPlayer(avatar);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void attack() {
        weaponComponent.setWeapon(new MeleeWeapon(1,1,1));
        GameObject testObject = new GameObject(new GraphicsComponent(new Sprite(1)));
        Tile attackTile = avatar.getTile().getUp();
        assertEquals(attackTile.getDown().getGameObject(), avatar, "Something went wrong setting the attacked Object");
        attackTile.setGameObject(testObject);
        transformComponent.setFacingDirection(Direction.UP);
        //avatar.attack(Direction.UP);
        assertTrue(((Hittable)testObject).isHit(), "GameObject was not hit");
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