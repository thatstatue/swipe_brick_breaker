package model;

import graphic.Render;
import logic.GamePanel;

import java.awt.*;

public class Brick extends Segment {
    int weight;
    public static final int BRICK_WIDTH = 98;
    public static final int BRICK_HEIGHT = 48;

    public void explode(){
        GamePanel.bricks.remove(this);

    }



    public Brick(int x, int y, int weight) {
        super(x, y);
        this.weight = weight;
        this.width = BRICK_WIDTH;
        this.height = BRICK_HEIGHT;
        this.color = Render.BRICK_DEFAULT_COLOR;
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
        g2D.fillRect(getX(), getY(), BRICK_WIDTH, BRICK_HEIGHT);
        g2D.setColor(Color.white);
        g2D.setFont(new Font("New Roman", Font.BOLD, 25));
        g2D.drawString(String.valueOf(weight), getX()+43, getY()+33);
    }
}