package segments;

import graphic.Render;

import java.awt.*;

public class Brick {
    Integer weight;
    int x, y;
    Color color;

    public Brick(Integer weight, int x, int y) {
        this.weight = weight;
        this.x = x;
        this.y = y;
        this.color = Render.BRICK_DEFAULT_COLOR;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
