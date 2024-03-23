package model;

import view.Drawable;
import controller.Config;
import controller.GameManager;
import controller.GamePanel;
import model.items.Item;


import java.awt.*;

public class Ball extends Segment implements Drawable, IntersectionControl {
    public static final int BALL_RADIUS = 12;
    private static final int BALL_HIT_LEFT = 1;
    private static final int BALL_HIT_RIGHT = 2;
    private static final int BALL_HIT_UP = 3;
    private static final int BALL_HIT_DOWN = 4;
    private static final int BALL_HIT_EDGE = 5;


    public static void setBallSpeed(int ballSpeed) {
        Ball.ballSpeed = ballSpeed;
    }

    public static int ballSpeed = Config.BALL_SPEED;
    public static Color color = Config.BALL_COLOR;
    private double degree;

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public Ball(int x, int y) {
        super(x, y);
        this.width = 2 * BALL_RADIUS;
        this.height = 2 * BALL_RADIUS;
        speed = ballSpeed;
        //xLocation = x;
        degree = Guideline.theta;
        color = Config.BALL_COLOR;
    }

    public Ball(int x) {
        this(x, yLocation);
    }

    private int speed;
    private boolean isMoving, isReturning;

    public void setReturning(boolean returning) {
        isReturning = returning;
    }

    public static int xLocation, yLocation = 600;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        Ball.color = color;
    }


    public static void setXLocation(int xLocation) {
        Ball.xLocation = xLocation;
    }

    public void setYSpeed0() {
        setY(600);
        degree = 0;
        setSpeed(20);
        if (getX() > xLocation) setSpeed(-20);
        isReturning = true;
    }


    public int getXSpeed() {
        return (int) (Math.cos(degree) * speed);
    }

    public int getYSpeed() {
        return (int) (Math.sin(degree) * speed);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public double getDegree() {
        return degree;
    }

    public void setDegree(double degree) {
        this.degree = degree;
    }

    public void move() {
        if (!isMoving && !isReturning) {
            setDegreeInRange();
            setSpeed(ballSpeed);
            isMoving = true;
        }
        int number = intersects();
        if (number >= 1 && number <= 5) {
            degree = -degree;
            if (number == BALL_HIT_LEFT ||
                    number == BALL_HIT_RIGHT || number == BALL_HIT_EDGE) {
                setSpeed(-getSpeed());
            }
        }
        int newX = getX() + getXSpeed();
        setX(Math.min(newX, Config.GAME_WIDTH - BALL_RADIUS * 2));
        if (getX() < 0) {
            setX(1);
            setDegree(-getDegree());
            setSpeed(-getSpeed());
        }
        int newY = getY() + getYSpeed();
        setY(newY);
        if (getY() > 600) {
            setYSpeed0();
        }
        // stop the ball at the correct location
        if (isReturning) {
            if (getX() <= xLocation + ballSpeed && getX() >= xLocation - ballSpeed) {
                setX(xLocation);
                setSpeed(0);
                isMoving = false;
            }
        }
    }

    private void setDegreeInRange() {
        if (degree < Math.PI/2) degree = Math.min(Guideline.theta, -0.15);
        degree = Math.max(degree, -3);
        if (degree > Math.PI/2) degree = -3;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(color);
        g2D.fillOval(getX(), getY(), 2 * BALL_RADIUS, 2 * BALL_RADIUS);
    }

    private int intersectBricks(int s1X, int s1Y, int s1EndX, int s1EndY) {
        for (Brick s2 : GamePanel.bricks) {
            int s2X = s2.getX();
            int s2Y = s2.getY();
            int s2EndX = s2.getWidth() + s2X;
            int s2EndY = s2.getHeight() + s2Y;
            boolean isTopLIn = s1X >= s2X && s1X <= s2EndX
                    && s1Y >= s2Y && s1Y <= s2EndY;
            boolean isTopRIn = s1EndX >= s2X && s1EndX <= s2EndX
                    && s1Y >= s2Y && s1Y <= s2EndY;
            boolean isBottLIn = s1X >= s2X && s1X <= s2EndX
                    && s1EndY >= s2Y && s1EndY <= s2EndY;
            boolean isBottRIn = s1EndX >= s2X && s1EndX <= s2EndX
                    && s1EndY >= s2Y && s1EndY <= s2EndY;

            if (isTopRIn || isTopLIn || isBottRIn || isBottLIn) {

                s2.setWeight(s2.getWeight() - Config.POWER);
                if (s2.getWeight() < 1) {
                    s2.explode();
                    if (s2 instanceof DanceBrick) {
                        GameManager.start10secsDance = GameManager.twentyMSs;
                        GameManager.isDance = true;
                    } else if (s2 instanceof QuakeBrick) {
                        GameManager.start10secsQuake = GameManager.twentyMSs;
                        GameManager.isQuake = true;
                    }
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

    private int intersectWall(Wall s2, int s1X, int s1Y, int s1EndX, int s1EndY) {
        int s2X = s2.getX();
        int s2Y = s2.getY();
        int s2EndX = s2.getWidth() + s2X;
        int s2EndY = s2.getHeight() + s2Y;

        boolean isTopLIn = s1X >= s2X && s1X <= s2EndX
                && s1Y >= s2Y && s1Y <= s2EndY;
        boolean isTopRIn = s1EndX >= s2X && s1EndX <= s2EndX
                && s1Y >= s2Y && s1Y <= s2EndY;
        boolean isBottLIn = s1X >= s2X && s1X <= s2EndX
                && s1EndY >= s2Y && s1EndY <= s2EndY;
        boolean isBottRIn = s1EndX >= s2X && s1EndX <= s2EndX
                && s1EndY >= s2Y && s1EndY <= s2EndY;

        if (isTopRIn || isTopLIn || isBottRIn || isBottLIn) {
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
        return 0;
    }

    private void intersectItems(int s1X, int s1Y, int s1EndX, int s1EndY) {
        for (int i = 0; i < GamePanel.items.size(); i++) {
            Item s2 = GamePanel.items.get(i);
            int s2X = s2.getX();
            int s2Y = s2.getY();
            int s2EndX = s2.getWidth() + s2X;
            int s2EndY = s2.getHeight() + s2Y;

            boolean isTopLIn = s1X >= s2X && s1X <= s2EndX
                    && s1Y >= s2Y && s1Y <= s2EndY;
            boolean isTopRIn = s1EndX >= s2X && s1EndX <= s2EndX
                    && s1Y >= s2Y && s1Y <= s2EndY;
            boolean isBottLIn = s1X >= s2X && s1X <= s2EndX
                    && s1EndY >= s2Y && s1EndY <= s2EndY;
            boolean isBottRIn = s1EndX >= s2X && s1EndX <= s2EndX
                    && s1EndY >= s2Y && s1EndY <= s2EndY;

            if (isTopRIn || isTopLIn || isBottRIn || isBottLIn) {
                s2.catched();
            }
        }
    }

    @Override
    public int intersects() {

        int s1X = getX();
        int s1Y = getY();
        int s1EndX = getWidth() + s1X;
        int s1EndY = getHeight() + s1Y;

        int ans = intersectBricks(s1X, s1Y, s1EndX, s1EndY);
        intersectItems(s1X, s1Y, s1EndX, s1EndY);

        if (ans != 0) return ans;
        int ans2 = intersectWall(GamePanel.rightWall, s1X, s1Y, s1EndX, s1EndY);
        int ans1 = intersectWall(GamePanel.leftWall, s1X, s1Y, s1EndX, s1EndY);
        if (ans1 != 0) return ans1;
        if (ans2 != 0) return ans2;
        if (s1EndY >= Config.GAME_HEIGHT - 50) {
            return BALL_HIT_DOWN;
        } else if (s1Y <= BALL_RADIUS) {
            setY(BALL_RADIUS * 2);
            return BALL_HIT_UP;
        }
        return 0;
    }
}
