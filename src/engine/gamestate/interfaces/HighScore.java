package engine.gamestate.interfaces;

import java.io.Serializable;
import java.util.List;

public interface HighScore extends Serializable {

    void updateHighscore(Score newScore);

    List<Score> getCurrentHighscore();
}
