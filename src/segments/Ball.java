package segments;

import graphic.Drawable;

import java.awt.*;

public class Ball implements Drawable {
    Integer weight;
    int x, y;
    Color color = Color.cyan;

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

    public Ball(Integer weight, int x, int y) {
        this.weight = weight;
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw() {

    }
}
