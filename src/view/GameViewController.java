package view;

import engine.view.DrawableTile;

import java.io.IOException;

public interface GameViewController  {


    /**
     * Method responsible for starting the gameloop after
     * necessary dependencies has been instantiated.
     */
    public void initializeGameEnv();


    /**
     * Method for the game enginee to call to update the state the view is supposed to draw to the screen.
     *
     * @param drawableMatrix a Tile 2D array containing tile objects to be drawn.
     */
    public void updateDrawableState(DrawableTile[][] drawableMatrix);

    void goToMenu() throws IOException;

    void goToDeathScreen() throws IOException;

    void stopGameLoop();

}
