package engine;

import engine.view.DrawableTile;
import view.GameViewController;

import java.io.IOException;
import java.io.Serializable;

public class GameViewStub implements GameViewController, Serializable{
    /**
     * Method responsible for starting the gameloop after
     * necessary dependencies has been instantiated.
     */
    @Override
    public void initializeGameEnv() {

    }

    /**
     * Method for the game enginee to call to update the state the view is supposed to draw to the screen.
     *
     * @param drawableMatrix a Tile 2D array containing tile objects to be drawn.
     */
    @Override
    public void updateDrawableState(DrawableTile[][] drawableMatrix) {

    }

    @Override
    public void goToMenu() throws IOException {

    }

    @Override
    public void goToDeathScreen() throws IOException {

    }

    @Override
    public void stopGameLoop() {

    }
}
