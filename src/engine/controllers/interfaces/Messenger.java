package engine.controllers.interfaces;

import engine.controllers.gamestate.messages.GameEventMessage;

public interface Messenger {

    void handleMessage(GameEventMessage message);

    void sendMessage(GameEventMessage message);
}
