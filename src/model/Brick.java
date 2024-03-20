package model;

import logic.Config;
import logic.GameManager;
import logic.GamePanel;

import java.awt.*;
import java.util.Random;

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
    public void drawQuake(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(color);
        Random random = new Random();
        int add = random.nextInt(10)-5;
        if ( getWidth() + add <20 || getHeight() + add <20){
            add = -add;
            if (getWidth() + add <20 || getHeight() + add <20){
                setWidth(getWidth()+20);
                setHeight(getHeight()+20);
            }
        }else if (getWidth() + add > BRICK_WIDTH*2.5 || getHeight() + add > BRICK_HEIGHT* 2.5){
            add = -add;
            if (getWidth() + add > BRICK_WIDTH*2.5 || getHeight() + add > BRICK_HEIGHT* 2.5){
                setWidth(getWidth()-5);
                setHeight(getHeight()-5);
            }
        }

        setWidth( getWidth() + add);
        setHeight( getWidth() + add);
        setX(getX() - add/2);
        if (getX()<0){
            setX(0);
        }else if (getX()> Config.GAME_WIDTH-getWidth()){
            setX(Config.GAME_WIDTH-getWidth());
        }
        setY(getY() - add/2 );

        g2D.fillRect(getX(), getY(), getWidth(), getHeight());
        g2D.setColor(Color.white);
        g2D.setFont(new Font("New Roman", Font.BOLD, 30));
        g2D.drawString(String.valueOf(weight), getX()+33, getY()+45);
    }
}