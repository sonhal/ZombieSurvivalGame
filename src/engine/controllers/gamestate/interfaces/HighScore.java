package engine.controllers.gamestate.interfaces;

import engine.controllers.gamestate.interfaces.Score;

import java.io.Serializable;
import java.util.List;

public interface HighScore extends Serializable {

    void updateHighscore(Score newScore);

    List<Score> getCurrentHighscore();
}
