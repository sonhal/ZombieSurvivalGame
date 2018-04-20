package engine.controllers.interfaces;

import java.util.List;

public interface HighScore {

    void updateHighscore(Score newScore);

    List<Score> getCurrentHighscore();
}
