package model.items;

import controller.Config;
import controller.GamePanel;
import model.Ball;

import java.awt.*;

import static controller.GameManager.moveUpsideBricks;

public class Upside extends Item {
    public Upside(int x, int y) {
        super(x, y);
        this.width = 3 * Ball.BALL_RADIUS;
        this.height = 2 * Ball.BALL_RADIUS;
        this.setColor(Config.UPSIDE_COLOR);

    }

    @Override
    public void catched() {
        moveUpsideBricks();
        GamePanel.items.remove(this);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(getColor());
        g2D.fillOval(getX(), getY(), getWidth(), getHeight());
        g2D.setColor(Color.white);
        g2D.setFont(new Font("New Roman", Font.BOLD, 20));
        g2D.drawString("U", getX() + Ball.BALL_RADIUS, getY() + Ball.BALL_RADIUS + 5);
    }
}
