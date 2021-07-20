package sample;

import java.util.Comparator;

/**
 * Comparator a HighScore osztályhoz.
 */
public class HighScoreComparator implements Comparator<HighScore> {
    @Override
    public int compare(HighScore o1, HighScore o2) {
        return Math.toIntExact(o1.getScore()-o2.getScore());
    }
}
