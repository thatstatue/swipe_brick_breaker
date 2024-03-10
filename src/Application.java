import logic.GamePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Application implements Runnable{
    private JFrame mainFrame;
    private JPanel mainPanel;
    private void initPanel(){
        mainFrame = new JFrame();
        mainPanel = new JPanel();

    }
    @Override
    public void run() {
        mainFrame = new JFrame("Main Window");
        mainFrame.setSize(800, 600);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        mainPanel = (JPanel) (mainFrame.getContentPane()); //casting
        mainPanel.setLayout(null);

        GamePanel myPanel = new GamePanel();
        mainPanel.add(myPanel);

        Timer timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               myPanel.repaint();
            }

        });
        timer.start();
    }
}
