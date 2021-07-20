package sample;

import java.io.Serializable;

/**
 * A pontszámok eltárolását segítő osztály.
 */
public class HighScore implements Serializable{
    private String name;
    private long score;

    /**
     * @return A HighScore-hoz tartozó név.
     */
    public String getName() {
        return name;
    }

    /**
     * @return A HighScore-hoz tartozó pontszám.
     */
    public long getScore() {
        return score;
    }

    /**
     * @param name A HighScore-hoz tartozó név.
     * @param score A HighScore-hoz tartozó osztály.
     */
    public HighScore(String name, long score) {
        this.name = name;
        this.score = score;

    }
}
