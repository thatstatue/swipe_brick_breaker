package graphic;

import logic.Config;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class ScoreData {
    private String playerName;
    private int score;
    private String dateFormat;
    private String ballColor;
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println("Current date and time: " +  dateFormat.format(date));
        return dateFormat.format(date);
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public ScoreData(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
        this.dateFormat = getDateTime();
    }

    public String getBallColor() {
        return ballColor;
    }

    public void setBallColor(String ballColor) {
        this.ballColor = ballColor;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public JPanel showInfo(){

        JPanel res = new JPanel(null);
        res.setBackground(Color.gray);
        res.setBounds(100, 300, 400, 220);
        String ans = "PLAYER NAME : " + getPlayerName();
        JLabel name = new JLabel(ans);
        name.setBounds(25, 50, 350, 40);
        name.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        res.add(name);

        String scr = "SCORE : "  + getScore() ;
        JLabel score = new JLabel(scr);
        score.setBounds(40, 100, 350, 40);
        score.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        res.add(score);

        String date = "DATE AND TIME : " + getDateFormat() ;
        JLabel day = new JLabel(date);
        day.setBounds(40, 150, 350, 40);
        day.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        res.add(day);

//        String col = "BALL COLOR : " + getBallColor() ;
//        JLabel color = new JLabel(col);
//        color.setBounds(40, 200, 350, 40);
//        color.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
//        res.add(color);

        res.setVisible(true);
        return res;
    }

    @Override
    public String toString(){
        return getPlayerName() + " |  score: "+ getScore();
    }
}
