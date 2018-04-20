package engine.controllers.interfaces;

import engine.controllers.gamestate.messages.GameEventMessage;

public interface MessengerMediator {

    void subscribe(Messenger object);

    void broadcast(GameEventMessage message);
}
