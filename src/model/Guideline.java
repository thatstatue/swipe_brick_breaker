package model;

import java.awt.*;


public class Guideline extends Segment{
    public Guideline(int x, int y, int endX, int endY) {
        super(x, y);
        this.endX = endX;
        this.endY = endY;
        theta = setThetaFromXY();
    }
    private double setThetaFromXY(){
        double deltaY = endY - this.getY() ;
        double deltaX = endX - this.getX() ;
        return Math.atan2(deltaY , deltaX);
    }

    public static double theta;
    private int endX, endY;

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Ball.color);
        g2D.setStroke(new BasicStroke(10));
        g2D.drawLine(getX()+15, getY()+15, getEndX(), getEndY());
    }
}
