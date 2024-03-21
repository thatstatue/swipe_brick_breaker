package model.items;

import model.Segment;

public abstract class Item extends Segment {
    private final int SPEED = 1;

    public Item(int x, int y) {
        super(x, y);
    }

    public void move() {

        setY(getY() + SPEED);

    }

    public abstract void catched();
}
