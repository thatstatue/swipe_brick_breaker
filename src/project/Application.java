package project;

import graphic.ScoreData;
import logic.Config;
import logic.GameManager;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Hashtable;

public class Application implements Runnable {

    private static Clip soundClip;

    public static int highScore;
    public static ArrayList<ScoreData> scoreBoard = new ArrayList<>();
    public static boolean inputSaveOn;
    private static JPanel data;
    private static boolean inputGuideline, inputThemeSong;
    public static JFrame jFrame;
    public static JPanel jPanel;
    private static GameManager gameManager;
    private static void showScoreBoard(){
        if (jPanel!= null) jFrame.remove(jPanel);
        jPanel = new JPanel(null);

        // Deserialization
        try {
            // Reading the object from a file
            FileInputStream file = new FileInputStream("file.ser");
            ObjectInputStream in = new ObjectInputStream(file);

            ScoreData scores= (ScoreData) in.readObject();
            scoreBoard.add(scores);


            in.close();
            file.close();
        }catch (IOException ex){
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String[] options= new String[scoreBoard.size()+1];
        options[0] = "-Select a Game-";
        int i = 1;
        for (ScoreData scoreData : scoreBoard){
            options[i] = scoreData.toString();
            i++;
        }
        JComboBox<String> dropdown = new JComboBox<>(options);

        dropdown.addActionListener(e -> {
            JComboBox<String> src = (JComboBox<String>) e.getSource();
            if (data!=null) jPanel.remove(data);
            data = new JPanel(null);
            data = scoreBoard.get(src.getSelectedIndex() - 1).showInfo();
            jPanel.add(data);
            jPanel.repaint();

        });

        dropdown.setBounds(100, 100, 400, 60);
        dropdown.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        jPanel.add(dropdown);



        JButton back = new JButton("Back to Menu");
        back.setBounds(200, 600, 200, 50);
        back.addActionListener(e -> showUI());
        jPanel.add(back);
        jFrame.add(jPanel);
        jFrame.setVisible(true);
    }
    private static void showSettings(){
        if (jPanel!= null) jFrame.remove(jPanel);
        jPanel = new JPanel(null);
        JLabel nameGL = new JLabel("GUIDELINE ");
        nameGL.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 21));

        JToggleButton inputGL = getInput(inputGuideline);
        nameGL.setBounds(100, 100, 200, 50);
        inputGL.setBounds(300, 100, 200, 50);
        inputGL.addChangeListener(e -> {
            inputGuideline = !inputGuideline;
            inputGL.setText("OFF");
            if (inputGuideline) inputGL.setText("ON");
        });

        jPanel.add(nameGL);
        jPanel.add(inputGL);

        JLabel nameTS = new JLabel("THEME SONG");
        nameTS.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 21));

        JToggleButton inputTS = getInput(inputThemeSong);
        nameTS.setBounds(100, 200, 200, 50);
        inputTS.setBounds(300, 200, 200, 50);
        inputTS.addChangeListener(e -> {
            inputThemeSong = !inputThemeSong;
            if (inputThemeSong) {
                soundClip.start();
                soundClip.loop(19);
            }else {
                soundClip.stop();
            }
            inputTS.setText("OFF");
            if (inputThemeSong) inputTS.setText("ON");
        });

        jPanel.add(nameTS);
        jPanel.add(inputTS);

        JLabel nameS = new JLabel("SAVE SCORES");
        nameS.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 21));

        JToggleButton inputS = getInput(inputSaveOn);
        nameS.setBounds(100, 300, 200, 50);
        inputS.setBounds(300, 300, 200, 50);
        inputS.addChangeListener(e -> {
            inputSaveOn = !inputSaveOn;
            inputS.setText("OFF");
            if (inputSaveOn) inputS.setText("ON");
        });

        jPanel.add(nameS);
        jPanel.add(inputS);

        JButton back = new JButton("Back to Menu");
        back.setBounds(200, 600, 200, 50);
        back.addActionListener(e -> showUI());
        jPanel.add(back);
        jPanel.repaint();
        jFrame.add(jPanel);
        jFrame.setVisible(true);
    }

    private static JToggleButton getInput(boolean inputBoolean) {
        JToggleButton input = new JToggleButton("OFF");
        if (inputBoolean) {
            input.setText("ON");
            input.setSelected(true);
        }
        input.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 21));
        return input;
    }


    @Override
    public void run() {
        jFrame = new JFrame("Swipe Brick Breaker");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(new Dimension(Config.GAME_WIDTH, Config.GAME_HEIGHT));
        jFrame.setLocationRelativeTo(null);
        jPanel = (JPanel) jFrame.getContentPane();
        jFrame.setResizable(false);

        try {
            File file = new File("");
            File soundFile = new File(file.getAbsolutePath() , "BGMusic.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            soundClip = AudioSystem.getClip();
            soundClip.open(audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (inputThemeSong) {
            soundClip.start();
            soundClip.loop(19);
        };
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
                case 0 -> setDifficulty(20, 1, 4, 4, 3);
                case 1 -> setDifficulty(25, 2, 3, 5, 4);
                case 2 -> setDifficulty(20, 2, 2, 6, 5);
            }

        });
        slider.setBounds(150, 230, 300, 50);
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
        JComboBox<String> dropdown = new JComboBox<>(
                new String[]{"-Select a color-", "Blue", "Green", "Pink", "Yellow" , "Orange"});
        dropdown.addActionListener(e -> {
            JComboBox<String> src = (JComboBox<String>) e.getSource();
            switch (src.getSelectedIndex()){
                case 1 -> Config.BALL_COLOR = Color.BLUE;
                case 2 -> Config.BALL_COLOR = Color.GREEN;
                case 3 -> Config.BALL_COLOR = Color.PINK;
                case 4 -> Config.BALL_COLOR = Color.YELLOW;
                case 5 -> Config.BALL_COLOR = Color.ORANGE;
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
            gameManager = new GameManager(input.getText(), inputGuideline );
            gameManager.playNewGame();
        });
        return confirm;
    }

    public static void showUI() {

        if (jPanel!= null) jFrame.remove(jPanel);
        jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));
        JLabel wc = new JLabel("SWIPE BRICK BREAKER");
        wc.setFont(new Font("Ink Free", Font.BOLD, 33));
        JPanel innerPanel = new JPanel(null);
        wc.setBounds(100,160,500,80);
        innerPanel.add(wc);
        JButton newG = new JButton("New Game");
        newG.addActionListener(e -> buildGame());
        newG.setBounds(200,280,180,50);
        innerPanel.add(newG);
        JLabel hs = new JLabel(" HIGH SCORE : " + highScore);
        hs.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        hs.setBounds(190,220,250,50);
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
}