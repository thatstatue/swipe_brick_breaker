package controller;

import view.ScoreData;
import view.Wrapper;
import model.Ball;
import model.Brick;
import model.Guideline;
import project.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;

import static controller.GamePanel.bricks;
import static project.Application.scoreBoard;

public class GameManager {
    public static GamePanel gamePanel;
    private final Wrapper wrapper;
    private final JLabel playerName;
    public static JLabel playerScore;
    public static int twentyMSs;
    public static int score;
    private final boolean guideOn;


    public static Timer timer;
    public static boolean ballsOnMove;
    public static boolean dizzyOn;
    public static int ballsOnMoveNumber;
    public static int start15secsSpeed, start15secsPower, start10secsDance, start10secsQuake;
    public static boolean isSpeed, isPower, isDance, isQuake;

    public GameManager(String playerName, boolean guideOn) {
        this.guideOn = guideOn;
        this.playerName = new JLabel(playerName);
        playerScore = setPlayerScore();
        wrapper = new Wrapper();
    }

    private JLabel setPlayerScore() {
        JLabel ans = new JLabel("SCORE: " + score);
        ans.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        ans.setBounds(230, 0, 400, 30);
        return ans;
    }

    private void setDefault() {
        Ball.setBallSpeed(Config.BALL_SPEED);
        isSpeed = false;
        isPower = false;
        dizzyOn = false;
        isQuake = false;
        isDance = false;
        ballsOnMove = false;
        start15secsSpeed = 0;
        start10secsDance = 0;
        start15secsPower = 0;
        start10secsQuake = 0;
        score = 0;
        playerScore.setText("SCORE: " + score);
        Config.POWER = 1;
        twentyMSs = 0;
    }

    public void playNewGame() {
        if (gamePanel != null) {
            Application.jPanel.remove(gamePanel);
        }
        gamePanel = new GamePanel();
        setDefault();

        Application.jPanel.add(gamePanel);
        playerName.setBounds(5, 0, 300, 30);
        playerName.setFont(new Font("New Roman", Font.BOLD, 21));
        playerName.setText(playerName.getText().toUpperCase());

        gamePanel.add(playerName);
        gamePanel.add(playerScore);

        gamePanel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (!ballsOnMove) {
                    Ball first = gamePanel.getFirstBall();
                    int x = e.getX();
                    int y = e.getY();
                    if (dizzyOn) {
                        x = new Random().nextInt(Config.GAME_WIDTH - 20);
                        y = new Random().nextInt(Config.GAME_HEIGHT - 100);
                    }
                    double theta = Math.atan2(y - first.getY(), x - first.getX());
                    boolean hitSegment = false;
                    while (!hitSegment) {
                        x += (int) (3 * Math.cos(theta));
                        y += (int) (3 * Math.sin(theta));
                        for (Brick brick : bricks) {
                            if (x >= brick.getX() && x <= brick.getX() + Brick.BRICK_WIDTH
                                    && y >= brick.getY() && y <= brick.getY() + Brick.BRICK_HEIGHT) {
                                hitSegment = true;
                                break;
                            }
                        }
                        if (x <= 0 || x >= Config.GAME_WIDTH || y <= 0 || y >= 570) {
                            hitSegment = true;
                        }
                    }
                    Guideline guideline = new Guideline(first.getX(), first.getY(), x, y);
                    if (guideOn) gamePanel.setGuideline(guideline);
                }
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
                    if (dizzyOn) dizzyOn = false;
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

        timer = new Timer(Config.DELAY, e -> {
            twentyMSs++;
            if (ballsOnMove) {
                gamePanel.moveBalls();
                ballsOnMoveNumber++;
            } else {
                ballsOnMoveNumber = 0;
                boolean isGameOver = gamePanel.moveBricks();
                gamePanel.moveItems();
                if (isGameOver) {
                    gameOver();
                }

            }
            if (twentyMSs % (1000 / Config.DELAY) == 0) {
                gamePanel.moveTime();
            }
            if (twentyMSs % (Config.CYCLE * 1000 / Config.DELAY) == 0) {
                gamePanel.addNewSegments();
            }
            if (isSpeed) {
                if (twentyMSs - start15secsSpeed >= 15 * (1000 / Config.DELAY)) {
                    Ball.ballSpeed = Config.BALL_SPEED;
                    for (Ball ball : gamePanel.getBalls()) {
                        if (ball.getSpeed() > 0) ball.setSpeed(Ball.ballSpeed);
                        else if (ball.getSpeed() < 0) ball.setSpeed(-Ball.ballSpeed);
                    }
                    isSpeed = false;
                    start15secsSpeed = 0;
                }
            }
            if (isPower) {
                Config.POWER = 2;
                if (twentyMSs - start15secsPower >= 15 * (1000 / Config.DELAY)) {
                    Config.POWER = 1;
                    isPower = false;
                    start15secsPower = 0;
                }
            } else {
                Config.POWER = 1;
            }
            if (isDance) {
                if (twentyMSs - start10secsDance >= 10 * (1000 / Config.DELAY)) {
                    isDance = false;
                    start10secsDance = 0;
                }
            }
            if (isQuake) {
                if (twentyMSs - start10secsQuake >= 10 * (1000 / Config.DELAY)) {
                    isQuake = false;
                    start10secsQuake = 0;
                }
            }
            gamePanel.repaint();
        });
        timer.start();
        Application.jFrame.setVisible(true);
    }

    public static void moveUpsideBricks() {
        gamePanel.moveUpsideBricks();
    }

    void gameOver() {
        gamePanel.gameOver();
        if (Application.inputSaveOn && score != 0) {
            ScoreData scoreData = new ScoreData(playerName.getText(), score);
            scoreBoard.add(scoreData);
            wrapper.setScoreBoard(scoreBoard);
            try {
                FileOutputStream file = new FileOutputStream("file.ser");
                ObjectOutputStream out = new ObjectOutputStream(file);

                //serialization
                out.writeObject(wrapper);
                out.close();
                file.close();

                System.out.println("Score has been saved");

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        if (score > Application.highScore) Application.highScore = score;

        String ans = showGameOverPopup();
        if (gamePanel != null) Application.jPanel.remove(gamePanel);


        switch (ans) {
            case "New Game" -> playNewGame();
            case "Preferences" -> Application.buildGame();
            default -> Application.showUI();
        }
        timer.stop();
    }

    public String showGameOverPopup() {
        String[] options = new String[]{"New Game", "Preferences", "Menu"};
        return options[JOptionPane.showOptionDialog(Application.jFrame,
                "                            GAME OVER", "game status",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, "Menu")];
    }
}
