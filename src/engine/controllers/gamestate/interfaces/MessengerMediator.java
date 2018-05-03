package engine.controllers.gamestate.interfaces;

import engine.controllers.gamestate.messages.GameEventMessage;
import engine.controllers.interfaces.Messenger;

public interface MessengerMediator {

    void subscribe(Messenger object);

    void broadcast(Messenger sender, GameEventMessage message);
}
