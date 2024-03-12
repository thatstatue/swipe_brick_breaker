
import logic.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Application implements Runnable {

    @Override
    public void run() {
        JFrame jFrame = new JFrame("Main");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel jPanel = (JPanel) jFrame.getContentPane();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jFrame.setSize(new Dimension(600, 750));
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        GamePanel gamePanel = new GamePanel();
        jPanel.add(gamePanel);


//        JButton button = new JButton("click here");
//        button.setBounds(20, 30, 100, 50);
//        // Set the size directly in setBounds
//        button.addMouseMotionListener(new MouseMotionListener() {
//            @Override public void mouseDragged(MouseEvent e) { button.setBackground(Color.cyan); }
//            @Override public void mouseMoved(MouseEvent e) { button.setBackground(Color.green); }
//        });

        Graphics2D graphics = (Graphics2D) jFrame.getGraphics();



        jPanel.add(gamePanel);

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("done this");
            }
        });
        timer.start();
        jFrame.setVisible(true);

//        jFrame.paintComponents(graphics);
//        jPanel.paintComponents(graphics);
//        jPanel.add(button);


//        jPanel.add(gamePanel);
//        jFrame.paintComponents(graphics);
//        jPanel.paintComponents(graphics);
//        gamePanel.paint(graphics);
//        ball.paintComponent(graphics);
//        gamePanel.repaint();


//        GameFrame frame = new GameFrame();
//        JPanel panel = new JPanel();
//        panel.setBounds(0 , 0 , 600, 700);
//        panel.setVisible(true);
//        panel.setFocusable(true);
//        panel.requestFocus();
//        panel.requestFocusInWindow();
//        panel.setLayout(null);
//        frame.setContentPane(panel);
//        panel = (JPanel)frame.getContentPane();
//
//        jpanel = new JPanel();
//        jpanel.setBounds(0 , 0 , 600, 700);
//        jpanel.setVisible(true);
//        jpanel.setFocusable(true);
//        jpanel.requestFocus();
//        jpanel.requestFocusInWindow();
//        jpanel.setLayout(null);
//        jpanel.setOpaque(false);
//
//        gamePanel = new GamePanel();
//        gamePanel.setLayout(null);

//        Ball ball = new Ball(5 , 70, 70);

//        panel.add(gamePanel);
//        panel.add(jpanel);
//        jpanel.add(ball);
//        jPanel.add(ball);
//        gamePanel.add(ball);
//        JButton button = new JButton("click here");
//        button.setBounds(20, 30, 50, 50);
//        button.setLocation(100, 100);
//        button.setSize(100, 50);
//        button.addMouseMotionListener(new MouseMotionListener() {
//            @Override
//            public void mouseDragged(MouseEvent e) {
//                button.setBackground(Color.cyan);
//            }
//
//            @Override
//            public void mouseMoved(MouseEvent e) {
//                button.setBackground(Color.green);
//            }
//        });
//        button.setVisible(true);
//        jPanel.add(button);
//        jFrame.setLocationRelativeTo(null);
//        frame.add(button);


    }
}