package engine.entities.composites;

/**
 * Represents the Sprite the view2D is to use to render a engine.entities.GameObject
 */
public class Sprite {

    private int spriteIndex;

    public Sprite(int spriteIndex) {
        this.spriteIndex = spriteIndex;
    }

    /**
     * Returns a index number the view2D uses to identify the sprite to render.
     * @return int
     */
    public int getSpriteIndex() {
        return spriteIndex;
    }
}