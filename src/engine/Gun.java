package engine;

import engine.composites.GraphicsComponent;
import engine.composites.Sprite;
import engine.composites.TransformComponent;

public class Gun extends Weapon {

    private ScriptableObjectController controller;
    private double lastActivateTime;
    private double activateDelay;

    public Gun(ScriptableObjectController controller, int range, int damage, double activateDelay) {
        super(range, damage);
        this.controller = controller;
        this.lastActivateTime = System.currentTimeMillis();
        this.activateDelay = activateDelay;

    }

    @Override
    public void activate( Tile fromTile, Direction direction) {
        if(canActivate()){
            System.out.println("Weapon activated!");
            Tile startTile = fromTile.getTileInDirection(direction);
            controller.addToUpdateList(new Bullet(controller, damage, startTile, direction));
        }
    }

    private boolean canActivate(){
        return System.currentTimeMillis() > this.lastActivateTime + activateDelay;
    }


}
