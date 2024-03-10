package logic;

import graphic.Data;
import graphic.Drawable;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {


    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        for (Drawable piece : Data.segments ){
            piece.draw();
        }
    }
}
