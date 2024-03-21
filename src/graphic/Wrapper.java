package graphic;

import java.io.Serializable;
import java.util.ArrayList;

public class Wrapper implements Serializable {
    private ArrayList<ScoreData> scoreBoard;

    public Wrapper() {
        scoreBoard = new ArrayList<>();
    }

    public ArrayList<ScoreData> getScoreBoard() {
        return scoreBoard;
    }

    public void setScoreBoard(ArrayList<ScoreData> scoreBoard) {
        this.scoreBoard = scoreBoard;
    }
}
