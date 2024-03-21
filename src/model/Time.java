package model;

import java.awt.*;

public class Time extends Segment {
    private int seconds, minutes;

    public Time(int x, int y) {
        super(x, y);
    }

    public void move() {
        if (seconds == 59) {
            minutes++;
            seconds = 0;
        } else {
            seconds++;
        }
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setFont(new Font("New Roman", Font.BOLD, 25));
        g2D.setColor(Color.darkGray);
        g2D.drawString(minutes + ":" +
                (seconds >= 10 ? String.valueOf(seconds) : "0" + seconds), getX(), getY());
    }
}
