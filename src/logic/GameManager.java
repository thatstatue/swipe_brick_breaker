package logic;

import model.Ball;
import model.Guideline;
import project.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameManager {
    public static GamePanel gamePanel;
    private final JLabel playerName;
    public static int twentyMSs;
    private JToggleButton soundOn;
    private int score;
    public static Timer timer;
    public static boolean ballsOnMove;
    public static int ballsOnMoveNumber , start15secs;
    public static boolean count15sec;

    public GameManager(String playerName){
        this.playerName = new JLabel( playerName);
    }

    public void playNewGame(){
        if (gamePanel!= null) {
            Application.jPanel.remove(gamePanel);
        }
        gamePanel = new GamePanel();

        Application.jPanel.add(gamePanel);
        playerName.setBounds(5, 0, 400, 30);
        playerName.setFont(new Font("New Roman", Font.BOLD, 21));
        playerName.setText(playerName.getText().toUpperCase());
        gamePanel.add(playerName);

        gamePanel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Ball first = gamePanel.getFirstBall();
                Guideline guideline = new Guideline(first.getX(), first.getY(), e.getX(), e.getY());
                gamePanel.setGuideline(guideline);
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                gamePanel.setGuideline(null);
            }
        });
        gamePanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!ballsOnMove) {
                    gamePanel.setGuideline(null);
                    gamePanel.unReturnBalls();
                    ballsOnMove = true;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        Application.jPanel.add(gamePanel);

        timer = new Timer(Config.DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                twentyMSs ++;
                if (ballsOnMove){
                    gamePanel.moveBalls();
                    ballsOnMoveNumber++;
                }else {
                    ballsOnMoveNumber = 0;
                    boolean isGameOver = gamePanel.moveBricks();
                    gamePanel.moveItems();
                    if (isGameOver){
                        gameOver();
                    }

                }
                if (twentyMSs % (1000/Config.DELAY) == 0){
                    gamePanel.moveTime();
                }
                if(twentyMSs % (Config.CYCLE * 1000/Config.DELAY) == 0){
                    gamePanel.addNewSegments();
                }
                if (count15sec){
                    if (twentyMSs - start15secs >= 15 * (1000/Config.DELAY)){
                        Ball.ballSpeed = Config.BALL_SPEED;
                        for (Ball ball : gamePanel.getBalls()){
                            if (ball.getSpeed()>0) ball.setSpeed(Ball.ballSpeed);
                            else if (ball.getSpeed()<0) ball.setSpeed(-Ball.ballSpeed);
                        }
                        count15sec = false;
                        start15secs = 0;
                    }
                }
                gamePanel.repaint();
            }
        });
        timer.start();
        Application.jFrame.setVisible(true);


    }

    public int getStartedNumber() {
        return ballsOnMoveNumber;
    }
    void gameOver(){
        gamePanel.gameOver();
        String ans = showGameOverPopup();
        if (gamePanel!= null) {
            Application.jPanel.remove(gamePanel);
        }
        switch (ans){
            case "New Game" -> playNewGame();
            case "Preferences" -> Application.buildGame();
            case "Menu" -> Application.showUI();
        }
        timer.stop();
    }
    public String showGameOverPopup() {
        String[] options = new String[]{"New Game", "Preferences", "Menu"};
        return options[JOptionPane.showOptionDialog(Application.jFrame,
                "                            GAME OVER", "game status",
                JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION,
                null, options, "New Game")];
    }


    public void setStartedNumber(int startedNumber) {
        GameManager.ballsOnMoveNumber = startedNumber;
    }
}
