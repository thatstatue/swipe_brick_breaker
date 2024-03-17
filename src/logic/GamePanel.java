package logic;

import model.*;
import model.items.Item;
import model.items.Speed;
import model.items.newBall;
import project.Application;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel {
    private Random random;
    private int turn;
    private Time time;
    public static int addedBalls = 1;
    public static ArrayList<Ball> balls , newBalls;
    public static ArrayList<Brick> bricks;
    public static ArrayList<Item> items;
    private Guideline guideline;



    public GamePanel() {
        setVisible(true);
        setLayout(null);
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
            if (brick.getY()>= 600- Brick.BRICK_HEIGHT ){
                return true;
            }
        }
        return false;
    }

    void gameOver(){
        this.setBackground(Color.gray);
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
        for (int i = 0 ; i < balls.size(); i ++){
            Ball ball = balls.get(i);
            if (GameManager.ballsOnMoveNumber >= Math.max(i , i*(4-(Ball.ballSpeed/9)))) {
                int deltaX = ball.getX();
                int deltaY = ball.getY();
                ball.move();
                if (ball.getY() == 600) {
                    newBalls.add(ball);
                    Ball.setxLocation(newBalls.getFirst().getX());
                }
                deltaX -= ball.getX();
                deltaY -= ball.getY();
                if (deltaY != 0 || deltaX != 0) {
                    noMoves = false;
                }
            }
        }
        if (noMoves){
            GameManager.ballsOnMove = false;
            System.out.println("balls are new");
            newBalls = new ArrayList<>();
            nextTurn();
        }
    }
    public void addNewSegments(){

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
            if (!GameManager.count15sec) items.add(new Speed(x , y));
        }

        int rand = random.nextInt(1, Config.RAND_BOUND_BRICK_NUMBER);
        for (int i = 0 ; i < rand; i++){
            Brick brick = new Brick(randX(), 10, random.nextInt(Config.RAND_BOUND_BRICK_WEIGHT) + turn);
            boolean brickOverlap = false;
            for (Brick brick1 : bricks){
                if (brick1.getX() == brick.getX() && brick1.getY()== 10){
                    brickOverlap = true;
                }
            }
            if (!brickOverlap) {
                bricks.add(brick);
            }
        }
    }
    private void nextTurn(){ //todo: improve fps
        turn ++;
        for (int i = 0 ; i < addedBalls; i ++) {
            Ball ball = new Ball(Ball.xLocation);
            balls.add(ball);
        }
        addedBalls = 1;

        //move bricks down one height
        for (Brick brick: bricks){
            brick.setY(brick.getY()+ Brick.BRICK_HEIGHT);
        }

        //todo: play sound
    }
    public Guideline getGuideline() {
        return guideline;
    }

    public void setGuideline(Guideline guideline) {
        this.guideline = guideline;
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public static void setBalls(ArrayList<Ball> balls) {
        GamePanel.balls = balls;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

//        g2d.setPaint(Color.yellow);
//        g2d.fillRect(400, 100, 50, 50);


        for (Brick brick: bricks) {
            brick.draw(g);
        }
        for (Item item : items){
            item.draw(g);
        }
        for (Ball ball : balls){
            ball.draw(g);
        }
        time.draw(g);

        if (guideline != null) guideline.draw(g);
//        for (Drawable piece : Data.segments ){
//            piece.draw(g2d);
//        }
    }
}
