package model;

import logic.GameManager;

import java.awt.*;

public class DanceBrick extends Brick{
    public DanceBrick(int x, int y, int weight) {
        super(x, y, weight);
        color = Color.MAGENTA;
    }
    @Override
    public void explode(){
        super.explode();
        GameManager.start10secs = GameManager.twentyMSs;
        GameManager.isDance = true;
    }
}
