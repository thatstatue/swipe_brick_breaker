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
    private int highScore;
    public static Timer timer;
    public static boolean ballsOnMove;
    public static int ballsOnMoveNumber ,  start15secs;
    private static boolean count15sec;

    public GameManager(String playerName){
        this.playerName = new JLabel("    " +playerName);
    }

    public void playNewGame(){

        gamePanel = new GamePanel();
        Application.jPanel.add(gamePanel);
        playerName.setBounds(5, 0, 200, 30);
        playerName.setFont(new Font("New Roman", Font.BOLD, 21));
//        playerName.setLocation(400, 200);
//        playerName.setMaximumSize(new Dimension(200,70));
        playerName.setText("PLAYER : NAME");
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

        timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                twentyMSs ++;
                if (ballsOnMove){
                    gamePanel.moveBalls();
                    ballsOnMoveNumber++;
                }else {
                    ballsOnMoveNumber = 0;
                    gamePanel.moveBricks();
                    gamePanel.moveItems();
                }
                if (twentyMSs % 50 == 0){
                    gamePanel.moveTime();
                }
                if (count15sec){
                    if (twentyMSs - start15secs == 15 * 50){
                        Ball.ballSpeed = Config.BALL_SPEED;
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

    public void setStartedNumber(int startedNumber) {
        GameManager.ballsOnMoveNumber = startedNumber;
    }
}
