package project;

import logic.Config;
import logic.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

public class Application implements Runnable {

    public static int highScore;

    public static JFrame jFrame;
    public static JPanel jPanel;
    private static GameManager gameManager;
    private static void showScoreBoard(){
        //todo
    }
    private static void showSettings(){
        //todo
    }


    @Override
    public void run() {
        jFrame = new JFrame("Main");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(new Dimension(Config.GAME_WIDTH, Config.GAME_HEIGHT));
        jFrame.setLocationRelativeTo(null);
        jPanel = (JPanel) jFrame.getContentPane();
        jFrame.setResizable(false);
        showUI();
    }

    static JSlider setDifficultyState(){
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 2, 0);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(0, new JLabel("EASY"));
        labelTable.put(1, new JLabel("MEDIUM"));
        labelTable.put(2, new JLabel("HARD "));
        slider.setLabelTable(labelTable);
        slider.addChangeListener(e -> {
            JSlider source = (JSlider) e.getSource();
            switch (source.getValue()){
                case 0 -> setDifficulty(20, 1, 5, 4, 3);
                case 1 -> setDifficulty(25, 2, 4, 5, 4);
                case 2 -> setDifficulty(20, 2, 3, 6, 5);
            }

        });
        slider.setBounds(150, 200, 300, 50);
        return slider;
    }
    private static void setDifficulty(int delay , int brickSpeed, int cycle, int randBoundBrickNum, int randBoundBrickWeight){
        Config.DELAY = delay;
        Config.BRICK_SPEED = brickSpeed;
        Config.CYCLE = cycle;
        Config.RAND_BOUND_BRICK_WEIGHT = randBoundBrickWeight;
        Config.RAND_BOUND_BRICK_NUMBER = randBoundBrickNum;
    }

    public static void buildGame(){
        if (jPanel!= null) jFrame.remove(jPanel);
        jPanel = new JPanel(null);
        JLabel name = new JLabel("PLAYER NAME : ");
        name.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 21));
        JTextField input = new JTextField(18);
        input.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 21));
        name.setBounds(100, 100, 200, 50);
        input.setBounds(300, 100, 200, 50);

        jPanel.add(name);
        jPanel.add(input);
        jPanel.add(setDifficultyState());

        JLabel color = new JLabel("BALL COLOR : ");
        color.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 21));
        JComboBox<String> dropdown = new JComboBox<>(new String[]{"Blue", "Green", "Pink", "Yellow" , "Orange"});
        dropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> src = (JComboBox<String>) e.getSource();
                System.out.println("bidgt");
                switch (src.getSelectedIndex()){
                    case 0 -> Config.BALL_COLOR = Color.BLUE;
                    case 1 -> Config.BALL_COLOR = Color.GREEN;
                    case 2 -> Config.BALL_COLOR = Color.PINK;
                    case 3 -> Config.BALL_COLOR = Color.YELLOW;
                    case 4 -> Config.BALL_COLOR = Color.ORANGE;
                }
            }
        });
        color.setBounds(120, 350, 200, 50);
        dropdown.setBounds(300, 350, 200, 50);
        dropdown.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 21));
        jPanel.add(color);
        jPanel.add(dropdown);


        JButton confirm = getConfirm(input);
        confirm.setBounds(250, 550, 100, 50);
        jPanel.add(confirm);
        jFrame.add(jPanel);
        jFrame.setVisible(true);
    }

    private static JButton getConfirm(JTextField input) {
        JButton confirm = new JButton("Start");
        confirm.addActionListener(e -> {
            if (jPanel!= null) jFrame.remove(jPanel);
            jPanel = new JPanel(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
            jPanel = (JPanel) jFrame.getContentPane();
            System.out.println(input.getText());
            gameManager = new GameManager(input.getText());
            gameManager.playNewGame();
        });
        return confirm;
    }

    public static void showUI() {
        if (jPanel!= null) jFrame.remove(jPanel);
        jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));
        JLabel wc = new JLabel("SWIPE BRICK BREAKER");
        wc.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        JPanel innerPanel = new JPanel(null);
        wc.setBounds(170,80,500,80);
        innerPanel.add(wc);
        JButton newG = new JButton("New Game");
        newG.addActionListener(e -> buildGame());
        newG.setBounds(200,280,180,50);
        innerPanel.add(newG);
        JLabel hs = new JLabel(" HIGH SCORE : " + highScore);
        hs.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        hs.setBounds(200,180,250,50);
        innerPanel.add(hs);

        JButton history = new JButton("Score Board");
        history.addActionListener(e -> showScoreBoard());
        history.setBounds(200,350,180,50);
        innerPanel.add(history);

        JButton settings = new JButton("Settings");
        settings.addActionListener(e -> showSettings());
        settings.setBounds(200,420,180,50);
        innerPanel.add(settings);

        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> jFrame.dispose());
        exit.setBounds(200,490,180,50);
        innerPanel.add(exit);

        jPanel.add(innerPanel);
        jFrame.add(jPanel);
        jFrame.setVisible(true);

    }

//todo: if num of hits > 20 in a sec :
}