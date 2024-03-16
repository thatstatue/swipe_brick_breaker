package project;

import logic.GameManager;
import logic.GamePanel;
import model.Ball;
import model.Guideline;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Application implements Runnable {
    public static final int MAIN_PANEL_WIDTH = 600;
    public static final int MAIN_PANEL_HEIGHT = 750;

    public static JFrame jFrame;
    public static JPanel jPanel;
    private GameManager gameManager;


    @Override
    public void run() {
        jFrame = new JFrame("Main");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jPanel = (JPanel) jFrame.getContentPane();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jFrame.setSize(new Dimension(MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT));
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);

        gameManager =new GameManager("your name");
        gameManager.playNewGame();



    }

//todo: if num of hits > 20 in a sec :
}