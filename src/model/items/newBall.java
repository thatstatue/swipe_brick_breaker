package model.items;

import logic.GamePanel;
import model.Ball;
import model.items.Item;

import java.awt.*;

public class newBall extends Item {

    public newBall(int x, int y) {
        super(x, y);
        this.width = 3* Ball.BALL_RADIUS;
        this.height =2*Ball.BALL_RADIUS;
    }

    @Override
    public void catched() {
        GamePanel.addedBalls ++;
        GamePanel.items.remove(this);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.cyan);
        g2D.fillOval(getX(), getY(), getWidth(),getHeight() );
        g2D.setColor(Color.white);
        g2D.setFont(new Font("New Roman", Font.BOLD, 20));
        g2D.drawString("B", getX()+Ball.BALL_RADIUS, getY()+Ball.BALL_RADIUS+ 5);
    }
}
