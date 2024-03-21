package model;

import logic.Config;

import java.awt.*;

public class Wall extends Segment {

    public Wall(int x, int y, int width, int height) {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Config.BG_COLOR);
        g2D.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}
