package engine.controllers.gamestate.messages;

import engine.controllers.interfaces.Level;

public class NewLevelMessage extends GameEventMessage {
    public NewLevelMessage(Level body) {
        super(body);
    }
}
