package engine.controllers.gamestate;

import engine.controllers.gamestate.messages.GameEventMessage;
import engine.controllers.interfaces.Messenger;
import engine.controllers.gamestate.interfaces.MessengerMediator;

import java.util.ArrayList;

public class GameStateMessengerMediator implements MessengerMediator {

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