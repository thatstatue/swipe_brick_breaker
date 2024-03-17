package logic;

import model.Brick;

import java.awt.*;

public class QuakeBrick extends Brick {
    public QuakeBrick(int x, int y, int weight) {
        super(x, y, weight);
        setColor(Color.BLUE);
    }

    @Override
    public void explode() {
        super.explode();
        GameManager.start10secs2 = GameManager.twentyMSs;
        GameManager.isQuake = true;
        //todo
    }
}
