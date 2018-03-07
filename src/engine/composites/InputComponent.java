package engine.composites;

import engine.ActionEvent;
import engine.Direction;

public class InputComponent {

    private TransformComponent transformComponent;
    private AttackComponent attackComponent;

    public InputComponent(TransformComponent transformComponent, AttackComponent attackComponent){
        this.transformComponent = transformComponent;
        this.attackComponent = attackComponent;
    };


    public void handle(ActionEvent event){

        switch (event){
            case MOVE_UP: transformComponent.move(Direction.UP);
            case MOVE_DOWN: transformComponent.move(Direction.DOWN);
            case MOVE_LEFT: transformComponent.move(Direction.LEFT);
            case MOVE_RIGHT: transformComponent.move(Direction.RIGHT);
            case ATTACK_UP: attackComponent.attack(Direction.UP);
            case ATTACK_DOWN: attackComponent.attack(Direction.DOWN);
            case ATTACK_LEFT: attackComponent.attack(Direction.LEFT);
            case ATTACK_RIGHT: attackComponent.attack(Direction.RIGHT);
            }
        }

}
