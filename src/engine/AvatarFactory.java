package engine;

import engine.composites.*;

public class AvatarFactory {


    public static Avatar create(Sprite sprite){
        TransformComponent tc = new TransformComponent();
        GraphicsComponent gc = new GraphicsComponent(sprite);
        AttackComponent ac = new AttackComponent(tc);
        InputComponent ic = new InputComponent(tc,ac);
        HealthComponent hc = new HealthComponent(10);
        return new Avatar(tc,gc, hc, ic, ac);
    }
}
