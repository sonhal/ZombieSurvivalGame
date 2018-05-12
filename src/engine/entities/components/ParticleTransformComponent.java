package engine.entities.components;

import engine.controllers.Direction;
import engine.entities.components.ComponentEvent.ComponentEvent;
import engine.entities.components.interfaces.TransformComponent;
import engine.entities.gameobjects.interfaces.GameObject;
import engine.world.Tile;

public class ParticleTransformComponent extends TransformComponent {

    private Tile currentTile;

    public ParticleTransformComponent(Tile connectedTile) {
        this.currentTile = connectedTile;
    }

    @Override
    public void update(GameObject gameObject) {
        //Do nothing
    }

    @Override
    public void handle(ComponentEvent event) {
        //Do nothing
    }

    @Override
    public void innit(GameObject gameObject) {
        currentTile.setParticleEffect(gameObject);
    }

    @Override
    public void cleanUp(GameObject gameObject) {
        if(currentTile.getParticleEffect() == gameObject){
            currentTile.clearParticleEffect();
        }
        currentTile = null;
    }

    @Override
    public Tile getCurrentTile() {
        return currentTile;
    }

    @Override
    public void setCurrentTile(Tile tile) {
        currentTile = tile;
    }

    @Override
    public Direction getFacingDirection() {
        return null;
    }

    @Override
    public void setFacingDirection(Direction facingDirection) {
        //Do nothing
    }

    @Override
    public void move(Direction direction, GameObject gameObject) {
        //Do nothing
    }
}
