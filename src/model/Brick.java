package model;

import logic.Config;
import logic.GameManager;
import logic.GamePanel;

import java.awt.*;

import static logic.GameManager.score;

public class Brick extends Segment {
    int weight, initialWeight;
    public static final int BRICK_WIDTH = 98;
    public static final int BRICK_HEIGHT = 68;

    private int scoreFunction(){
        // initWeight - number of five seconds passed
        return initialWeight - (GameManager.twentyMSs /(1000/Config.DELAY))/5;
    }

    public void explode(){
        GamePanel.bricks.remove(this);
        score += scoreFunction();
        System.out.println(scoreFunction());
        System.out.println(score);
        GameManager.playerScore.setText("SCORE: " + score);
    }



    public Brick(int x, int y, int weight) {
        super(x, y);
        this.weight = weight;
        this.width = BRICK_WIDTH;
        this.height = BRICK_HEIGHT;
        this.color = Config.BRICK_COLOR;
        initialWeight = weight;
    }

    public void move(){

        setY(getY()+ Config.BRICK_SPEED);

    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(color);
        g2D.fillRect(getX(), getY(), getWidth(), getHeight());
        g2D.setColor(Color.white);
        g2D.setFont(new Font("New Roman", Font.BOLD, 30));
        g2D.drawString(String.valueOf(weight), getX()+33, getY()+45);
    }
}