package engine.controllers;

import engine.GameViewStub;
import engine.services.save.SaveGameHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameHandlerTest {
    GameViewStub stub;
    @BeforeEach
    void setUp() {
        stub = new GameViewStub();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createWorld() {
    }

    @Test
    void createWorld1() {
    }

    @Test
    void updateWordState() {
    }

    @Test
    void getDrawableWorld() {
    }

    @Test
    void sendEvent() {
    }

    @Test
    void handlePlayerDeath() {
    }

    @Test
    void getPlayer() {
        GameHandler gameHandler = new GameHandler(stub);
        assertNotNull(gameHandler.getPlayer());
    }

    @Test
    void getWorld() {
    }

    @Test
    void saveGame(){
        GameHandler gameHandler = new GameHandler(stub);
        gameHandler.saveGame();
    }

    @Test
    void loadGame(){
        GameHandler gameHandler = new GameHandler(stub);
        gameHandler.loadGame();
    }

}