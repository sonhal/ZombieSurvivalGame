package engine.gamestate;

import engine.gamestate.messages.GameEventMessage;
import engine.controllers.interfaces.Messenger;
import engine.gamestate.interfaces.MessengerMediator;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Mediator class that relays messages between otherwise decoupled game domains.
 */
public class GameStateMessengerMediator implements MessengerMediator, Serializable {

    private ArrayList<Messenger> subscribers;

    public GameStateMessengerMediator(){
        subscribers = new ArrayList<>();
    }

    @Override
    public void subscribe(Messenger newSubscriber) {
        subscribers.add(newSubscriber);
    }

    @Override
    public void broadcast(Messenger sender, GameEventMessage message) {
        subscribers.forEach(recipient -> {if(recipient != sender)recipient.handleMessage(message);} );
    }
}