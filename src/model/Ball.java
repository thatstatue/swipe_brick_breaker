package model;

import graphic.Data;
import graphic.Drawable;
import logic.GamePanel;
import project.Application;

import java.awt.*;

public class Ball extends Segment implements Drawable {
    public static final int BALL_RADIUS = 15;
    private static final int BALL_HIT_LEFT = 1;
    private static final int BALL_HIT_RIGHT = 2;
    private static final int BALL_HIT_UP = 3;
    private static final int BALL_HIT_DOWN = 4;
    private static final int BALL_HIT_EDGE = 5;

    private static final int BALL_SPEED = 5;
    public static Color color = Color.cyan;

    public Ball(int x, int y) {
        super(x, y);
        this.width = 2*BALL_RADIUS;
        this.height = 2*BALL_RADIUS;
        background = Data.projectData().getBall();
        speed = BALL_SPEED;
    }
    private int speed;

    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        Ball.color = color;
    }

    public int getXSpeed() {
        return (int)(Math.cos(Guideline.theta) * speed);
    }
    public int getYSpeed() {
        return (int)(Math.sin(Guideline.theta) * speed);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
    public void move(){
        int number = intersects();
        if (number != 0){
            if(number!= BALL_HIT_EDGE) {
                Guideline.theta = -Guideline.theta;
            }
            if (number == BALL_HIT_LEFT ||
            number == BALL_HIT_RIGHT || number == BALL_HIT_EDGE) {
                setSpeed( -speed);
            }
            System.out.print("hit from ");
            switch (number){
                case BALL_HIT_DOWN -> System.out.println("down");
                case BALL_HIT_UP -> System.out.println("up");
                case BALL_HIT_LEFT -> System.out.println("left");
                case BALL_HIT_RIGHT -> System.out.println("right");
                case BALL_HIT_EDGE -> System.out.println("edge");
            }
        }
        setX(getX()+getXSpeed());
        setY(getY()+getYSpeed());
    }
//    @Override
//    public void paintComponent(Graphics g){
//        super.paintComponent(g);
//        Graphics g2D = (Graphics2D) g;
//        g2D.drawImage(background, getX() , getY(), BALL_RADIUS, BALL_RADIUS, null);
//    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(background, getX() , getY(), getWidth(), getHeight(), null);
//        g2D.setColor(color);
//        g2D.fillOval(getX(), getY(), BALL_RADIUS, BALL_RADIUS);
    }

    public int intersects() {

        int s1X = getX();
        int s1Y = getY();
        int s1EndX = getWidth() + s1X;
        int s1EndY = getHeight() + s1Y;



//        System.out.println(s1X + "   " + s1Y);
//        System.out.println(s1EndX + "   " + s1EndY);
        if (s1X <= 0){
            return BALL_HIT_LEFT;
        }else if( s1EndY >= Application.MAIN_PANEL_HEIGHT - 50 ){
            return BALL_HIT_DOWN;
        }else if (s1Y <= 0 ){
            return BALL_HIT_UP;
        }else if ( s1EndX >= Application.MAIN_PANEL_WIDTH) {
            return BALL_HIT_RIGHT;
        }

        for (Brick s2: GamePanel.bricks) {
            int s2X = s2.getX();
            int s2Y = s2.getY();

            int s2EndX = s2.getWidth()+ s2X;
            int s2EndY = s2.getHeight()+ s2Y;
            boolean isTopLIn = s1X >= s2X && s1X <= s2EndX
                    && s1Y >= s2Y && s1Y <= s2EndY;
            boolean isTopRIn =  s1EndX >= s2X && s1EndX <= s2EndX
                    && s1Y >= s2Y && s1Y <= s2EndY;
            boolean isBottLIn =  s1X >= s2X && s1X <= s2EndX
                    && s1EndY >= s2Y && s1EndY <= s2EndY;
            boolean isBottRIn =  s1EndX >= s2X && s1EndX <= s2EndX
                    && s1EndY >= s2Y && s1EndY <= s2EndY;

            if (isTopRIn || isTopLIn || isBottRIn || isBottLIn) {
                if (s2.getWeight() <= 1) {
                    s2.explode();
                }else {
                    s2.setWeight(s2.getWeight() - 1);
                }
                if (isTopLIn && isBottLIn) {

                    return BALL_HIT_LEFT;
                }
                if (isTopRIn && isBottRIn) {
                    return BALL_HIT_RIGHT;
                }
                if (isBottRIn && isBottLIn) {
                    return BALL_HIT_DOWN;
                }
                if (isTopRIn && isTopLIn) {
                    return BALL_HIT_UP;
                }


                return BALL_HIT_EDGE;
            }
        }
        return 0;
    }

}
