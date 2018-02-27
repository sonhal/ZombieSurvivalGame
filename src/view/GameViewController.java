package view;

import engine.DrawableMatrix;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public interface GameViewController  {

    public void startGame();
    public void updateDrawableState(DrawableMatrix drawableMatrix);

}
