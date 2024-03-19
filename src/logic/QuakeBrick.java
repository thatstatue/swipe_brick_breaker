package logic;

import model.Brick;

import java.awt.*;

public class QuakeBrick extends Brick {
    public QuakeBrick(int x, int y, int weight) {
        super(x, y, weight);
        setColor(Color.pink);
    }
}
