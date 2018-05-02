package engine.controllers.gamestate.messages;

import engine.controllers.interfaces.Level;

public class NewLevelMessage implements GameEventMessage {

    private Level newLevel;

    public NewLevelMessage(Level newLevel) {
        this.newLevel = newLevel;
    }

    @Override
    public Level messageBody() {
        return newLevel;
    }
}
