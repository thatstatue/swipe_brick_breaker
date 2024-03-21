package logic;

import model.*;
import model.items.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static logic.GameManager.isQuake;
import static logic.GameManager.timer;

public class GamePanel extends JPanel {
    private final Random random;
    private int turn;
    private boolean isPause;
    private final Time time;
    public static int addedBalls = 1;
    public static ArrayList<Ball> balls , newBalls;
    public static ArrayList<Brick> bricks;
    public static ArrayList<Item> items;
    public static Wall rightWall, leftWall;
    private Guideline guideline;
    private final JLabel numberOfBalls;


    public GamePanel() {
        setVisible(true);
        setLayout(null);
        turn = 0;
        random = new Random();
        time = new Time(500, 25);
        balls = new ArrayList<>();

        balls.add(new Ball(280, 600));

        bricks = new ArrayList<>();
        Brick brick1 = new Brick(randX(), 100, random.nextInt(1,4));
        Brick brick2 = new Brick(randX(), 200, random.nextInt(1,3));

        bricks.add(brick1);
        bricks.add(brick2);

        newBalls = new ArrayList<>();
        turn = 1;

        items = new ArrayList<>();

        numberOfBalls = new JLabel("x" + 1);
        numberOfBalls.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        numberOfBalls.setBounds(280,620,50,50);
        this.add(numberOfBalls);

        JButton pause;
        try {
            BufferedImage pbutton = ImageIO.read(new File("button.png"));
            pause = new JButton(new ImageIcon(pbutton));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        pause.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        pause.setBounds(8,635,70,70);
        pause.addActionListener(e -> {
            if (isPause) timer.start();
            else timer.stop();
            isPause = !isPause;
        });
        this.add(pause);

        leftWall = new Wall(-100,-10,100, Config.GAME_HEIGHT + 50);
        rightWall = new Wall(Config.GAME_WIDTH,-10,100, Config.GAME_HEIGHT + 50);

    }
    private int randX(){
        int randX = random.nextInt(Config.GAME_WIDTH / Brick.BRICK_WIDTH);
        return randX * Brick.BRICK_WIDTH;
    }

    public Ball getFirstBall(){
        return balls.getFirst();
    }

    public void unReturnBalls(){
        for (Ball ball : balls) {
            ball.setReturning(false);
            ball.setSpeed(Ball.ballSpeed);
            ball.setDegree(Guideline.theta);
        }
    }
    public boolean moveBricks(){
        for (Brick brick : bricks){
            brick.move();
            if (brick.getY()>= 600- brick.getHeight() ){
                return true;
            }
        }
        return false;
    }

    void gameOver(){
        this.setBackground(Color.gray);
    }

    public void moveUpsideBricks() {
        for (Brick brick : bricks) {
            brick.moveUpside();
        }
    }

    public void moveTime(){
        time.move();
    }
    public void moveItems() {
        for (Item item: items){
            item.move();
        }
    }
    public void moveBalls(){
        boolean noMoves = true;
        ArrayList<Integer> removingBalls = new ArrayList<>();
        for (int i = 0 ; i < balls.size(); i ++){
            Ball ball = balls.get(i);
            if (GameManager.ballsOnMoveNumber >= Math.max(i , i*(4-(Ball.ballSpeed/9)))) {
                int deltaX = ball.getX();
                int deltaY = ball.getY();
                ball.move();
                for (int j = 0 ; j < bricks.size()-1; j++){
                    Brick brick1 = bricks.get(j);
                    Brick brick2 = bricks.get(j+1);
                    int x = ball.getX() + Ball.BALL_RADIUS;
                    int y = ball.getY() + Ball.BALL_RADIUS;
                    boolean xBetween1 = x >= brick1.getWidth() + brick1.getX()
                            && x <= brick2.getX();

                    boolean xBetween2 = ball.getX() >= brick2.getWidth() + brick2.getX()
                            && ball.getX() <= brick1.getX();

                    boolean yBetween1 = y >= brick1.getHeight() + brick1.getY()
                            && y <= brick2.getY();
                    boolean yBetween2 = y >= brick2.getHeight() + brick2.getY()
                            && y <= brick1.getY();
                    boolean xBricks = Math.min(
                            Math.abs(brick2.getX() + brick2.getWidth() - brick1.getX()),
                            Math.abs(brick2.getX() - brick1.getWidth() - brick1.getX()))
                            <= 4*Ball.BALL_RADIUS;
                    boolean yBricks = Math.min(
                            Math.abs(brick2.getY() + brick2.getHeight() - brick1.getY()),
                            Math.abs(brick2.getY() - brick1.getHeight() - brick1.getY()))
                            <= 4*Ball.BALL_RADIUS;

                    if ( xBricks && yBricks && (xBetween1 || xBetween2) && (yBetween1 || yBetween2)){
                        removingBalls.add(i);
                    }
                }
                if (ball.getY() == 600) {
                    newBalls.add(ball);
                    Ball.setXLocation(newBalls.getFirst().getX());
                }
                deltaX -= ball.getX();
                deltaY -= ball.getY();
                if (deltaY != 0 || deltaX != 0) {
                    noMoves = false;
                }
            }
        }
        if (isQuake) {
            removingBalls.sort((o1, o2) -> {
                if (o1 >o2) return -1;
                else if (o1 < o2) return 1;
                return 0;
            });
            for (Integer i : removingBalls) {
                System.out.println("EXPLODE!");
                balls.remove(balls.get(i));
            }
        }
        if (noMoves){
            GameManager.ballsOnMove = false;
            newBalls = new ArrayList<>();
            nextTurn();
        }
    }
    public void addNewSegments(){
        //add items
        int isAddBall = random.nextInt(turn);
        for (int i = 0 ; i < random.nextInt(3); i++){
            if (isAddBall != turn - 1){
                int x = random.nextInt(50, Config.GAME_WIDTH - 50);
                int y = random.nextInt(50, Ball.yLocation - 50);
                items.add(new newBall(x, y));
            }
        }
        if (isAddBall % 6 == 0){
            int x = random.nextInt(50, Config.GAME_WIDTH - 50);
            int y = random.nextInt(50, Ball.yLocation - 50);
            if (!GameManager.isSpeed) items.add(new Speed(x , y));
        }
        if (isAddBall % 7 == 1){
            int x = random.nextInt(50, Config.GAME_WIDTH - 50);
            int y = random.nextInt(50, Ball.yLocation - 50);
            if (!GameManager.isPower) items.add(new Power(x , y));
        }
        if (isAddBall % 8 == 5){
            int x = random.nextInt(50, Config.GAME_WIDTH - 50);
            int y = random.nextInt(50, Ball.yLocation - 50);
            if (!GameManager.dizzyOn) items.add(new Dizzy(x , y));
        }
        if (isAddBall % 11 == 9){
            int x = random.nextInt(50, Config.GAME_WIDTH - 50);
            int y = random.nextInt(50, Ball.yLocation - 50);
            items.add(new Upside(x , y));
        }

        //add bricks
        int rand = random.nextInt(1, Config.RAND_BOUND_BRICK_NUMBER);
        for (int i = 0 ; i < rand; i++){
            Brick brick;
            int rand2 =random.nextInt(100);
            if(rand2 % 7 == 3){
                brick = new QuakeBrick(randX(), 10, random.nextInt(Config.RAND_BOUND_BRICK_WEIGHT) + turn);
            }else if (rand2 % 7 == 4) {
                brick = new DanceBrick(randX(), 10, random.nextInt(Config.RAND_BOUND_BRICK_WEIGHT) + turn);
            }else{
                brick = new Brick(randX(), 10, random.nextInt(Config.RAND_BOUND_BRICK_WEIGHT) + turn);
            }
            boolean brickOverlap = false;
            for (Brick brick1 : bricks){
                if (brick1.getX() >= brick.getX() && brick1.getX() <= brick.getX() + Brick.BRICK_WIDTH
                        && brick1.getY() + Brick.BRICK_HEIGHT >= brick.getY()) {
                    brickOverlap = true;
                    break;
                }
            }
            if (!brickOverlap) {
                bricks.add(brick);
            }
        }
    }
    private void nextTurn(){
        turn ++;
        for (int i = 0 ; i < addedBalls; i ++) {
            Ball ball = new Ball(Ball.xLocation);
            balls.add(ball);
        }
        addedBalls = 1;
        numberOfBalls.setText("x"+ balls.size());

        //move bricks down one height
        for (Brick brick: bricks){
            brick.setY(brick.getY()+ Brick.BRICK_HEIGHT);
        }
    }
    public void setGuideline(Guideline guideline) {
        this.guideline = guideline;
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        if (GameManager.isDance && GameManager.twentyMSs % 8 ==0){
            for (Brick brick: bricks) {
                brick.setColor(new Color(random.nextInt(256),random.nextInt(256),
                        random.nextInt(256),random.nextInt(256)));
                if (isQuake) brick.drawQuake(g);
                else brick.draw(g);
            }
            for (Item item : items){
                item.setColor(new Color(random.nextInt(256),random.nextInt(256),
                        random.nextInt(256),random.nextInt(256)));
                item.draw(g);
            }
            for (Ball ball : balls){
                ball.setColor(new Color(random.nextInt(256),random.nextInt(256),
                        random.nextInt(256),random.nextInt(256)));
                ball.draw(g);
            }
            this.setBackground(new Color(random.nextInt(256),random.nextInt(256),
                    random.nextInt(256),random.nextInt(256)));
        }else if(GameManager.isDance){
            for (Brick brick : bricks) {
                if (isQuake) brick.drawQuake(g);
                else brick.draw(g);
            }
            for (Item item : items) {
                item.draw(g);
            }
            for (Ball ball : balls) {
                ball.draw(g);
            }
        }else{
            this.setBackground(Config.BG_COLOR);
            for (Brick brick : bricks) {
                brick.setColor(Config.BRICK_COLOR);
                if (brick instanceof DanceBrick)
                    brick.setColor(Config.DANCE_BRICK_COLOR);
                else if (brick instanceof QuakeBrick)
                    brick.setColor(Config.QUAKE_BRICK_COLOR);
                if (isQuake) brick.drawQuake(g);
                else brick.draw(g);

            }
            for (Item item : items) {
                item.draw(g);
            }
            for (Ball ball : balls) {
                ball.setColor(Config.BALL_COLOR);
                ball.draw(g);
            }
        }
        time.draw(g);

        if (guideline != null) guideline.draw(g);
    }
}
