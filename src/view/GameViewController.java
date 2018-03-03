package view;

import engine.DrawableMatrix;
import engine.DrawableTile;
import engine.Tile;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public interface GameViewController  {


    /**
     * Method responsible for starting the gameloop after
     * necessary dependencies has been instantiated.
     */
    public void startGame();


    /**
     * Method for the game enginee to call to update the state the view is supposed to draw to the screen.
     *
     * @param drawableMatrix a Tile 2D array containing tile objects to be drawn.
     */
    public void updateDrawableState(DrawableTile[][] drawableMatrix);

}
