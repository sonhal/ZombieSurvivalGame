package engine.composites;

import javafx.scene.canvas.GraphicsContext;

public class GraphicsComponent implements Component {

    private Sprite sprite;

    public GraphicsComponent(Sprite sprite){
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
