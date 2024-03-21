package model.items;

import logic.Config;
import logic.GameManager;
import logic.GamePanel;
import model.Ball;

import java.awt.*;

public class Speed extends Item {
    public Speed(int x, int y) {
        super(x, y);
        this.width = 3 * Ball.BALL_RADIUS;
        this.height = 2 * Ball.BALL_RADIUS;
    }

    @Override
    public void catched() {
        GameManager.start15secsSpeed = GameManager.twentyMSs;
        GameManager.isSpeed = true;
        Ball.setBallSpeed(Config.BALL_SPEED * 2);
        for (Ball ball : GamePanel.balls) {
            if (ball.getSpeed() > 0) ball.setSpeed(Ball.ballSpeed);
            else if (ball.getSpeed() < 0) ball.setSpeed(-Ball.ballSpeed);
        }
        GamePanel.items.remove(this);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.yellow);
        g2D.fillOval(getX(), getY(), getWidth(), getHeight());
        g2D.setColor(Color.white);
        g2D.setFont(new Font("New Roman", Font.BOLD, 20));
        g2D.drawString("S", getX() + Ball.BALL_RADIUS, getY() + Ball.BALL_RADIUS + 5);
    }
}
