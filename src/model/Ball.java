package model;

import graphic.Data;
import graphic.Drawable;

import java.awt.*;

public class Ball extends Segment implements Drawable {
    public static final int BALL_RADIUS = 30;

    Color color = Color.cyan;

    public Ball(int x, int y) {
        super(x, y);
        background = Data.projectData().getBall();
    }
//    @Override
//    public void paintComponent(Graphics g){
//        super.paintComponent(g);
//        Graphics g2D = (Graphics2D) g;
//        g2D.drawImage(background, getX() , getY(), BALL_RADIUS, BALL_RADIUS, null);
//    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(background, getX() , getY(), BALL_RADIUS, BALL_RADIUS, null);
//        g2D.setColor(color);
//        g2D.fillOval(getX(), getY(), BALL_RADIUS, BALL_RADIUS);
    }
}
