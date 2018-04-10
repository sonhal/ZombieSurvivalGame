package engine.entities.interfaces;

import engine.entities.composites.AvatarTransformComponent;
import engine.entities.composites.InputComponent;
import engine.entities.composites.TransformComponent;

public interface Collidable {

    TransformComponent getTransformComponent();

    InputComponent getInputComponent();
}
