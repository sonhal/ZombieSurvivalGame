package engine.controllers.gamestate.messages;

public class NewLevelMessage implements GameEventMessage {

    private int newLevel;

    public NewLevelMessage(int newLevel) {
        this.newLevel = newLevel;
    }

    @Override
    public Integer messageBody() {
        return newLevel;
    }
}
