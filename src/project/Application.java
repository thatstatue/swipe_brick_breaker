package project;

import logic.GamePanel;
import model.Ball;
import model.Guideline;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Application implements Runnable {
    public static final int MAIN_PANEL_WIDTH = 600;
    public static final int MAIN_PANEL_HEIGHT = 750;
    private boolean ballsOnMove;

    @Override
    public void run() {
        JFrame jFrame = new JFrame("Main");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel jPanel = (JPanel) jFrame.getContentPane();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jFrame.setSize(new Dimension(MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT));
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        GamePanel gamePanel = new GamePanel();
        jPanel.add(gamePanel);
        gamePanel.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                Ball first = gamePanel.getFirstBall();
                Guideline guideline = new Guideline(first.getX(), first.getY(), e.getX(), e.getY());
                gamePanel.setGuideline(guideline);

//                Graphics graphics = jFrame.getGraphics();
//
//                guideline.draw(graphics);
                System.out.println(e.getX() + " " + e.getY());

            }
            @Override
            public void mouseMoved(MouseEvent e) {
                gamePanel.setGuideline(null);
            }
        });
//        JLabel label = new JLabel();
//        label.g
        gamePanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!ballsOnMove) {
                    System.out.println("THISSSSSSSSSSSSSSSSSSS");
                    Ball ball = gamePanel.getFirstBall();
                    System.out.println(ball.getX() + "  " + ball.getY());
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


        Graphics2D graphics = (Graphics2D) jFrame.getGraphics();



        jPanel.add(gamePanel);

        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ballsOnMove){
                    gamePanel.moveBalls();
                }
                gamePanel.repaint();
            }
        });
        timer.start();
        jFrame.setVisible(true);


    }
}