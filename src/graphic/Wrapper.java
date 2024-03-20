package graphic;

import java.io.Serializable;
import java.util.ArrayList;

public class Wrapper implements Serializable {
    private ArrayList<ScoreData> scoreBoard;
    public Wrapper(){
        scoreBoard = new ArrayList<>();
    }
    public Wrapper(ArrayList<ScoreData> scoreBoard){
        this.scoreBoard = scoreBoard;
    }
    public void addScore (ScoreData scoreData){
        scoreBoard.add(scoreData);
        System.out.println();
        for (ScoreData scoreData1 : scoreBoard){
            System.out.println(scoreData1);
        }
        System.out.println("=============");
    }
    public ArrayList<ScoreData> getScoreBoard() {
        return scoreBoard;
    }

    public void setScoreBoard(ArrayList<ScoreData> scoreBoard) {
        this.scoreBoard = scoreBoard;
    }
}
