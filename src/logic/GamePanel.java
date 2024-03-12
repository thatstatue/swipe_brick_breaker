package logic;

import model.Ball;
import model.Brick;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private ArrayList<Ball> balls ;
    private ArrayList<Brick> bricks;

    public GamePanel() {
        setVisible(true);
        setLayout(null);
        balls = new ArrayList<>();
        Ball ball = new Ball(500, 500);
        balls.add(ball);
        bricks = new ArrayList<>();
        Brick brick1 = new Brick(100, 100, 1); //todo: unhard
        Brick brick2 = new Brick(200, 100, 2);
        bricks.add(brick1);
        bricks.add(brick2);
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
//        for (Drawable piece : Data.segments ){
//            piece.draw(g2d);
//        }
    }
}
