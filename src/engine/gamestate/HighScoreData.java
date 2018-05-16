package engine.gamestate;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;

public class HighScoreData implements Serializable {

    private TreeSet<HighScoreEntry> highscoreSet;
    private int listSize;

    public HighScoreData(int listSize){
        highscoreSet = new TreeSet<>();
        this.listSize = listSize;
    }

    public boolean add(int score){
        if(highscoreSet.size() < listSize){
            highscoreSet.add(new HighScoreEntry(score));
            return true;
        }
        return false;
    }

    public Set<HighScoreEntry> getHighscoreSet() {
        return highscoreSet.descendingSet();
    }

    public class HighScoreEntry implements Comparable, Serializable{
        public final LocalDateTime timePoint;
        public final int score;

        HighScoreEntry(int score){
            this.score = score;
            this.timePoint = LocalDateTime.now();
        }

        public String getTimePoint() {
            return timePoint.toString();
        }

        public int getScore() {
            return score;
        }

        @Override
        public String toString() {
            return "date: " + timePoint + " | score: " + score;
        }

        @Override
        public int compareTo(Object other) {
            HighScoreEntry o = (HighScoreEntry) other;

            int score = this.score;
            int oScore = ((HighScoreEntry) other).score;

            int time = this.timePoint.getSecond();
            int oTime = ((HighScoreEntry) other).score;

            if (score < oScore) {
                return -1;
            } else if (score > oScore) {
                return 1;
            } else {
                if (time > oTime) {
                    return 1;
                }
                else if(time < oTime){
                    return -1;
                }
                else {
                    return 0;
                }
        }

    }

    }
}
