package logic;

import model.Ball;
import model.Brick;
import model.Guideline;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    public static ArrayList<Ball> balls ;
    public static ArrayList<Brick> bricks;
    private Guideline guideline;

    public GamePanel() {
        setVisible(true);
        setLayout(null);
        balls = new ArrayList<>();
        Ball ball = new Ball(280, 600);
        balls.add(ball);
        bricks = new ArrayList<>();
        Brick brick1 = new Brick(100, 100, 5); //todo: unhard
        Brick brick2 = new Brick(300, 100, 6);
        bricks.add(brick1);
        bricks.add(brick2);
    }

    public Ball getFirstBall(){
        return balls.getFirst();
    }
    public void moveBalls(){
        for (Ball ball : balls){
            ball.move();
        }
    }

    public Guideline getGuideline() {
        return guideline;
    }

    public void setGuideline(Guideline guideline) {
        this.guideline = guideline;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

//        g2d.setPaint(Color.yellow);
//        g2d.fillRect(400, 100, 50, 50);


        for (Brick brick: bricks) {
            brick.draw(g);
        }
        for (Ball ball : balls){
            ball.draw(g);
        }
        if (guideline != null) guideline.draw(g);
//        for (Drawable piece : Data.segments ){
//            piece.draw(g2d);
//        }
    }
}
