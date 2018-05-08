package engine.gamestate.messages;

public class EnemyKilledMessage implements GameEventMessage {

    private int valueOfEnemyKilled;

    public EnemyKilledMessage(int valueOfEnemyKilled){
        this.valueOfEnemyKilled = valueOfEnemyKilled;
    }
    @Override
    public Integer messageBody() {
        return valueOfEnemyKilled;
    }
}
